package org.morm.record.compo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.exception.SormException;
import org.morm.record.Entity;
import org.morm.record.field.Field;
import org.morm.session.SessionFactory;

public class ManyToOne<TID, E extends Entity> extends Field<TID> {
	// implements Clonable<ManyToOne<TID, E>> {

	protected final Field<TID> selfFkField;
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

	public Field<TID> doClone() {
		// TODO
		throw new SormException("internal error, invoking " + getClass() + "#doClone()");
	}

	public ManyToOne<TID, E> doCloneCollaboration() {
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
			this.collaboration = Entity.loadById(foreignEntityClass, selfFkField.getValue());
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
