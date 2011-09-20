package org.morm.record.compo;

import org.morm.record.Entity;
import org.morm.record.field.FieldDef;
import org.morm.record.identity.IdentityGenerator;

import java.util.List;

public class OneToMany<TID, E extends Entity> {

	protected IdentityGenerator<TID> selfIdFieldRef;
	protected final Class<E> foreignEntityClass;
	protected final FieldDef<TID> foreignFieldDef;
	protected E foreignEntity;

	protected List<E> collaboration;
	protected boolean isInit = false;

	public OneToMany(final Class<E> foreignEntityClass, final FieldDef<TID> foreignField) {
		this.foreignEntityClass = foreignEntityClass;
		this.foreignFieldDef = foreignField;
	}

	public void setSelfIdFieldRef(final IdentityGenerator<TID> selfIdFieldRef) {
		this.selfIdFieldRef = selfIdFieldRef;
	}

	public String getColumnName() {
		return selfIdFieldRef.getColumnName();
	}

	public OneToMany<TID, E> doClone() {
		final OneToMany<TID, E> r = new OneToMany<TID, E>(foreignEntityClass, foreignFieldDef);
		r.setSelfIdFieldRef(selfIdFieldRef);
		return r;
	}

	public List<E> getCollaboration() {
		if (!isInit) {
			if (selfIdFieldRef.getValue() == null) {
				this.isInit = true;
				return null;
			}

			// XXX XXX XXX XXX XXX XXX XXX XXX XXX XXX XXX XXX
			// final Class<E> casterForeignEntityClass = foreignEntityClass;
			// this.foreignEntity =
			// SingletonFactory.getEntity(casterForeignEntityClass);
			//
			// // TODO aquesta QueryObject és constant, no cal construir-la cada
			// // cop
			// final IQueryObject q = new QueryObject()
			// /**/.append("SELECT * FROM ")
			// /**/.append(foreignEntity.getTableName())
			// /**/.append(" WHERE ")
			// /**/.append(foreignFieldDef.getColumnName())
			// /**/.append("=?")
			// /**/.addParams(this.selfIdFieldRef.getValue())
			// /**/;
			// final IRowMapper<E> rowMapper =
			// this.foreignEntity.getRowMapper();
			// this.collaboration = DataMapper.query(rowMapper, q);
			//
			// this.collaboration = (List<E>)
			// SessionFactory.getSession().getIdentityMap().loadOrStore(
			// (List<Entity>) this.collaboration);
			// XXX XXX XXX XXX XXX XXX XXX XXX XXX XXX XXX XXX

			this.collaboration = Entity.loadByColumn(foreignEntityClass, foreignFieldDef.getColumnName(),
					selfIdFieldRef.getValue());

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
		/**/(getCollaboration() == null ? "null" : getCollaboration().toString()) :
		/**/"[...]";
	}

	public FieldDef<TID> getForeignField() {
		return foreignFieldDef;
	}

	public boolean getIsInit() {
		return isInit;
	}

}
