package org.morm.record.compo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.mapper.DataMapper;
import org.morm.mapper.IRowMapper;
import org.morm.record.Entity;
import org.morm.record.QueryObject;
import org.morm.record.SingletonFactory;
import org.morm.record.field.Field;

public class ManyToOne<TID, E extends Entity> extends Field<TID> {

	protected final Field<TID> selfFkField;
	protected final Class<E> foreignEntityClass;
	protected String foreigIdFieldColumnName;

	protected E foreignEntity;

	public ManyToOne(final Field<TID> selfFkField, final Class<E> foreignEntityClass) {
		super(selfFkField.getColumnName());

		this.selfFkField = selfFkField;
		this.foreignEntityClass = foreignEntityClass;
	}

	@Override
	public TID getValue() {
		return selfFkField.getValue();
	}

	@Override
	public void setValue(final TID value) {
		this.selfFkField.setValue(value);
	}

	@Override
	public Field<TID> doClone() {
		throw new RuntimeException();
	}

	public ManyToOne<TID, E> doCloneCollaboration() {
		return new ManyToOne<TID, E>(selfFkField.doClone(), foreignEntityClass);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		selfFkField.load(rs);
	}

	// -------------

	protected E collaboration;
	protected boolean isInit = false;

	@SuppressWarnings("unchecked")
	public E getCollaboration() {
		if (!isInit) {
			if (selfFkField.getValue() == null) {
				this.isInit = true;
				return null;
			}

			this.foreignEntity = (E) SingletonFactory.get((Class<Entity>) foreignEntityClass);
			this.foreigIdFieldColumnName = foreignEntity.getIdField().getColumnName();

			// * Rabbit.Dog = SELECT 1 FROM DOG WHERE DOG.ID_DOG=rabbit.idDog
			// *
			// * tableNameOf(Dog.class)
			// * pkOf(DOG).toFkOf(RABBIT)
			final QueryObject q = new QueryObject()
			/**/.append("SELECT * FROM ")
			/**/.append(this.foreignEntity.getTableName())
			/**/.append(" WHERE ")
			/**/.append(this.foreigIdFieldColumnName)
			/**/.append("=?")
			/**/.addParams(this.selfFkField.getValue());
			/**/;
			this.collaboration = DataMapper.queryUnique((IRowMapper<E>) this.foreignEntity.getRowMapper(), q);
			this.isInit = true;
		}
		return this.collaboration;
	}

	public void setCollaboration(final E collaboration) {
		this.collaboration = collaboration;
		this.isInit = true;
	}

	@Override
	public String toString() {
		return getColumnName() + "=" + getValue() + "=>" + (isInit ? getCollaboration() : "[...]");
	}

	public boolean getIsInit() {
		return isInit;
	}

}
