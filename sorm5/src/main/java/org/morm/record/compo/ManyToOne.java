package org.morm.record.compo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.mapper.DataMapper;
import org.morm.record.Entity;
import org.morm.record.QueryObject;
import org.morm.record.field.Field;

public class ManyToOne<TID, E extends Entity> extends Field<TID> {

	protected final Field<TID> selfFkField;
	protected final Class<E> foreignEntityClass;
	protected final Field<?> foreigIdField;

	protected final E foreignEntity;

	public ManyToOne(final Field<TID> selfFkField, final Class<E> foreignEntityClass,
			final Field<?> foreigIdField) {

		super(selfFkField.getColumnName());

		this.selfFkField = selfFkField;
		this.foreignEntityClass = foreignEntityClass;
		this.foreigIdField = foreigIdField;

		try {
			this.foreignEntity = foreignEntityClass.newInstance();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Field<TID> doClone() {
		return new ManyToOne<TID, E>(selfFkField.doClone(), foreignEntityClass, foreigIdField);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		selfFkField.load(rs);
	}

	@Override
	public void loadAggregate(final ResultSet rs) throws SQLException {
		selfFkField.loadAggregate(rs);
	}

	@Override
	public void store(final PreparedStatement pstm, final int index) throws SQLException {
		selfFkField.store(pstm, index);
	}

	// -------------

	protected E collaboration;
	protected boolean isInit = false;

	// TODO
	@SuppressWarnings("unchecked")
	public E getCollaboration() {
		if (!isInit) {
			// * Rabbit.Dog = SELECT 1 FROM DOG WHERE DOG.ID_DOG=rabbit.idDog
			// *
			// * tableNameOf(Dog.class)
			// * pkOf(DOG).toFkOf(RABBIT)
			final QueryObject q = new QueryObject()
			/**/.append("SELECT * FROM ")
			/**/.append(this.foreignEntity.getTableName())
			/**/.append(" where ")
			/**/.append(this.foreignEntity.getTableName())
			/**/.append(".")
			/**/.append(this.foreigIdField.getColumnName())
			/**/.append("=")
			/**/.append("?")
			/**/.addParams(selfFkField.getValue());
			/**/;
			this.collaboration = (E) DataMapper.queryUnique(this.foreignEntity.getMapper(), q);
			this.isInit = true;
		}
		return this.collaboration;
	}

	public void setCollaboration(final E collaboration) {
		this.collaboration = collaboration;
		this.isInit = true;
	}

}
