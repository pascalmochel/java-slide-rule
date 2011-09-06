package org.morm.record.compo;

import java.util.List;

import org.morm.mapper.DataMapper;
import org.morm.mapper.IRowMapper;
import org.morm.record.Entity;
import org.morm.record.QueryObject;
import org.morm.record.SingletonFactory;
import org.morm.record.field.Field;
import org.morm.record.identity.IdentityGenerator;

public class OneToMany<TID, E extends Entity> {

	protected IdentityGenerator<TID> selfIdFieldRef;
	protected final Class<E> foreignEntityClass;
	protected final Field<TID> foreignField;
	protected E foreignEntity;

	public OneToMany(final Class<E> foreignEntityClass, final Field<TID> foreignField) {
		this.foreignEntityClass = foreignEntityClass;
		this.foreignField = foreignField;
	}

	public void setSelfIdFieldRef(final IdentityGenerator<TID> selfIdFieldRef) {
		this.selfIdFieldRef = selfIdFieldRef;
	}

	public String getColumnName() {
		return selfIdFieldRef.getColumnName();
	}

	public OneToMany<TID, E> doCloneCollaboration() {
		final OneToMany<TID, E> r = new OneToMany<TID, E>(foreignEntityClass, foreignField);
		r.setSelfIdFieldRef(selfIdFieldRef);
		return r;
	}

	protected List<E> collaboration;
	protected boolean isInit = false;

	// * Dog.List&lt;Rabbit&gt; = SELECT * FROM RABBIT WHERE ID_DOG=$dog.idDog
	// *
	// * tableNameOf(Rabbit.class)
	// * foreignOf(RABBIT).toPkOf(DOG)
	@SuppressWarnings("unchecked")
	public List<E> getCollaboration() {
		if (!isInit) {
			if (selfIdFieldRef.getValue() == null) {
				this.isInit = true;
				return null;
			}
			this.foreignEntity = (E) SingletonFactory.get((Class<Entity>) foreignEntityClass);

			final QueryObject q = new QueryObject()
			/**/.append("SELECT * FROM ")
			/**/.append(this.foreignEntity.getTableName())
			/**/.append(" WHERE ")
			/**/.append(foreignField.getColumnName())
			/**/.append("=?")
			/**/.addParams(this.selfIdFieldRef.getValue());
			/**/;
			this.collaboration = DataMapper.query((IRowMapper<E>) this.foreignEntity.getRowMapper(), q);
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

	public boolean getIsInit() {
		return isInit;
	}

}
