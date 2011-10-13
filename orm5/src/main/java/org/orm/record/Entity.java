package org.orm.record;

import org.orm.criteria.Criterion;
import org.orm.exception.OrmException;
import org.orm.mapper.DataMapper;
import org.orm.mapper.IRowMapper;
import org.orm.query.IQueryObject;
import org.orm.query.QueryObject;
import org.orm.record.field.Field;
import org.orm.record.field.FieldDef;
import org.orm.record.field.compo.ManyToOne;
import org.orm.record.field.compo.OneToMany;
import org.orm.session.SessionFactory;

import java.util.List;

// TODO té sentit un TxInterceptor?
// TODO i que passa fent herència d'entitats?
public class Entity extends BaseEntity {

	public static final boolean CASCADED_DELETE = true;

	/**
	 * @return number of affected rows
	 */
	public static int sqlStatement(final String query, final Object... params) {
		return DataMapper.update(new QueryObject(query, params));
	}

	public void store() {
		SessionFactory.getSession().getStoredSet().clear();
		innerStore();
	}

	@SuppressWarnings("unchecked")
	private void innerStore() {

		if (SessionFactory.getSession().getStoredSet().isStored(this)) {
			return;
		}

		for (final ManyToOne<?, ?> c : getManyToOnes()) {
			if (c.getIsInit()) {
				final Entity v = c.getCollaboration();
				v.innerStore();
				set((FieldDef<Object>) c, v.getIdField().getValue());
			}
		}

		if (getIdField().getValue() == null) {
			insert();
		} else {
			update();
		}

		SessionFactory.getSession().getIdCache().attachForce((Class<Entity>) getClass(),
				getIdField().getValue(), this);

		for (final OneToMany<?, ?> c : getOneToManies()) {
			if (c.getIsInit()) {
				final List<? extends Entity> cs = c.getCollaboration(this);
				if (cs != null) {
					for (final Entity e : cs) {
						e.set(((Field<Object>) c.getForeignField()), getIdField().getValue());
						e.innerStore();
					}
				}
			}
		}
	}

	public void insert() {

		if (getIdField().generateBefore()) {
			getIdField().assignGeneratedValue();
		}

		final IQueryObject query = new QueryObject()
		/**/.append("INSERT INTO ")
		/**/.append(getTableName())
		/**/.append(" (")
		/**/.append(QueryGenUtils.columnNamesJoin(getFields()))
		/**/.append(") VALUES (")
		/**/.append(QueryGenUtils.parametersJoin(getFields()))
		/**/.append(")")
		/**/.addParams(QueryGenUtils.fieldValues(getFields()))
		/**/;

		DataMapper.update(query);

		if (!getIdField().generateBefore()) {
			getIdField().assignGeneratedValue();
		}
	}

	private void update() {

		final IQueryObject query = new QueryObject()
		/**/.append("UPDATE ")
		/**/.append(getTableName())
		/**/.append(" SET ")
		/**/.append(QueryGenUtils.setColumnNamesExceptId(getIdField(), getFields()))
		/**/.append(" WHERE ")
		/**/.append(getIdField().getColumnName())
		/**/.append("=?")
		/**/.addParams(QueryGenUtils.fieldValuesIdLast(getIdField(), getFields()))
		/**/;

		final int affectedRows = DataMapper.update(query);
		if (affectedRows > 1) {
			throw new OrmException("updated more than 1 row");
		}
	}

	public void delete() {
		if (CASCADED_DELETE) {
			for (final ManyToOne<?, ?> c : getManyToOnes()) {
				final Entity collaboration = c.getCollaboration();
				if (collaboration != null) {
					collaboration.delete();
				}
			}
		}
		final IQueryObject query = new QueryObject()
		/**/.append("DELETE FROM ")
		/**/.append(getTableName())
		/**/.append(" WHERE ")
		/**/.append(getIdField().getColumnName())
		/**/.append("=?")
		/**/.addParams(getIdField().getValue())
		/**/;
		DataMapper.update(query);
	}

	public void delete(final Criterion criterion) {
		if (CASCADED_DELETE) {
			for (final ManyToOne<?, ?> c : getManyToOnes()) {
				final Entity collaboration = c.getCollaboration();
				if (collaboration != null) {
					collaboration.delete();
				}
			}
		}
		final IQueryObject query = new QueryObject()
		/**/.append("DELETE FROM ")
		/**/.append(getTableName())
		/**/.append(criterion.renderQuery())
		/**/;
		DataMapper.update(query);
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> IRowMapper<T> getRowMapper() {
		return (IRowMapper<T>) SingletonFactory.getEntityMapper(getClass());
	}

}
