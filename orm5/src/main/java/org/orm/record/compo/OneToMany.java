package org.orm.record.compo;

import org.orm.record.BaseEntity;
import org.orm.record.Entity;
import org.orm.record.field.Clonable;
import org.orm.record.field.FieldDef;
import org.orm.session.SessionFactory;

import java.util.List;

public class OneToMany<TID, E extends Entity> implements Clonable<OneToMany<TID, E>> {

	// protected IdentityGenerator<TID> selfIdFieldRef;
	protected final Class<E> foreignEntityClass;
	protected final FieldDef<TID> foreignFieldDef;
	protected E foreignEntity;

	protected List<E> collaboration;
	protected boolean isInit = false;

	public OneToMany(final Class<E> foreignEntityClass, final FieldDef<TID> foreignField) {
		this.foreignEntityClass = foreignEntityClass;
		this.foreignFieldDef = foreignField;
	}

	public OneToMany<TID, E> doClone() {
		final OneToMany<TID, E> r = new OneToMany<TID, E>(foreignEntityClass, foreignFieldDef);
		return r;
	}

	public List<E> getCollaboration(final BaseEntity baseEntity) {
		if (!isInit) {
			final Object selfIdFieldValue = baseEntity.getIdField().getValue();
			if (selfIdFieldValue == null) {
				this.isInit = true;
				return null;
			}

			SessionFactory.getSession().open();
			this.collaboration = Entity.loadByColumn(foreignEntityClass, foreignFieldDef.getColumnName(),
					selfIdFieldValue);
			SessionFactory.getSession().closeReadOnly();

			this.isInit = true;
		}
		return this.collaboration;
	}

	public void setCollaboration(final List<E> collaboration) {
		this.collaboration = collaboration;
		this.isInit = true;
	}

	@Override
	public String toString() {
		return isInit ?
		/**/(collaboration == null ? "null" : collaboration.toString()) :
		/**/"[...]";
	}

	public FieldDef<TID> getForeignField() {
		return foreignFieldDef;
	}

	public boolean getIsInit() {
		return isInit;
	}

}