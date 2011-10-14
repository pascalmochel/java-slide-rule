package org.mb.record.field.compo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.mb.criteria.Criteria;
import org.mb.record.Entity;
import org.mb.record.field.Field;
import org.mb.session.SessionFactory;

public class ManyToOne<TID, E extends Entity> extends Field<TID> {

	protected final Field<TID> selfFkField;
	protected final Class<E> foreignEntityClass;
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

	public ManyToOne<TID, E> doClone() {
		return new ManyToOne<TID, E>(selfFkField.doClone(), foreignEntityClass);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		selfFkField.load(rs);
	}

	public E getCollaboration() {
		if (!isInit) {
			if (selfFkField.getValue() == null) {
				this.isInit = true;
				return null;
			}

			SessionFactory.getSession().open();
			this.collaboration = Criteria.selectById(foreignEntityClass, selfFkField.getValue());
			SessionFactory.getSession().closeReadOnly();

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
