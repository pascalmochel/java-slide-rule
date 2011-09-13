package org.morm.record.compo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.exception.SormException;
import org.morm.mapper.DataMapper;
import org.morm.mapper.IRowMapper;
import org.morm.query.QueryObject;
import org.morm.record.Entity;
import org.morm.record.SingletonFactory;
import org.morm.record.field.Field;
import org.morm.session.SessionFactory;

public class ManyToOne<TID, E extends Entity> extends Field<TID> {

	protected Field<TID> selfFkField;
	protected final Class<E> foreignEntityClass;
	protected String foreignIdFieldColumnName;
	protected E foreignEntity;

	protected E collaboration;
	protected boolean isInit = false;

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
		throw new SormException("internal error, invoking " + getClass() + "#doClone()");
	}

	public ManyToOne<TID, E> doCloneCollaboration() {
		return new ManyToOne<TID, E>(selfFkField.doClone(), foreignEntityClass);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		selfFkField.load(rs);
	}

	@SuppressWarnings("unchecked")
	public E getCollaboration() {
		if (!isInit) {
			if (selfFkField.getValue() == null) {
				this.isInit = true;
				return null;
			}

			final Class<? extends E> castedForeignEntityClass = foreignEntityClass;
			this.foreignEntity = SingletonFactory.get(castedForeignEntityClass);
			this.foreignIdFieldColumnName = foreignEntity.getIdField().getColumnName();

			final QueryObject q = new QueryObject()
			/**/.append("SELECT * FROM ")
			/**/.append(foreignEntity.getTableName())
			/**/.append(" WHERE ")
			/**/.append(foreignIdFieldColumnName)
			/**/.append("=?")
			/**/.addParams(selfFkField.getValue())
			/**/;
			final IRowMapper<E> rowMapper = this.foreignEntity.getRowMapper();
			this.collaboration = DataMapper.queryUnique(rowMapper, q);
			this.collaboration = (E) SessionFactory.getSession().getIdentityMap().loadOrStore(
					this.collaboration);

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
