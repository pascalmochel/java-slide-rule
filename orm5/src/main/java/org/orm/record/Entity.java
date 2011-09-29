package org.orm.record;

import java.util.List;

import org.orm.criteria.Criteria;
import org.orm.criteria.Criterion;
import org.orm.exception.OrmException;
import org.orm.mapper.DataMapper;
import org.orm.mapper.IRowMapper;
import org.orm.query.IQueryObject;
import org.orm.query.MutableQueryObject;
import org.orm.query.QueryObject;
import org.orm.record.compo.ManyToOne;
import org.orm.record.compo.OneToMany;
import org.orm.record.field.Field;
import org.orm.record.field.FieldDef;
import org.orm.session.SessionFactory;

//TODO té sentit un TxInterceptor?
public class Entity extends BaseEntity {

	public static final boolean CASCADED_DELETE = true;

	public static <T extends Entity> T loadById(final Class<T> entityClass, final Object id) {
		return SingletonFactory.getEntity(entityClass).ploadById(entityClass, id);
	}

	public <T extends Entity> T loadUniqueByQuery(final Class<T> entityClass, final QueryObject query) {
		return SingletonFactory.getEntity(entityClass).ploadUniqueByQuery(entityClass, query);
	}

	public static <T extends Entity> List<T> loadBy(final Class<T> entityClass, final Criterion... criterions) {
		return SingletonFactory.getEntity(entityClass).ploadBy(entityClass, criterions);
	}

	public static <T extends Entity> T loadUniqueBy(final Class<T> entityClass, final Criterion... criterions) {
		return SingletonFactory.getEntity(entityClass).ploadUniqueBy(entityClass, criterions);
	}

	public static <T extends Entity> List<T> loadAll(final Class<T> entityClass) {
		return SingletonFactory.getEntity(entityClass).ploadAll(entityClass);
	}

	public static <T extends Entity> List<T> loadByQuery(final Class<T> entityClass, final QueryObject query) {
		return SingletonFactory.getEntity(entityClass).ploadByQuery(entityClass, query);
	}

	public static <T extends Entity> List<T> loadByColumn(final Class<T> entityClass, final String column,
			final Object value) {
		return SingletonFactory.getEntity(entityClass).ploadByColumn(entityClass, column, value);
	}

	public static int sqlStatement(final QueryObject query) {
		return DataMapper.update(query);
	}

	public static int sqlStatement(final String query, final Object... params) {
		return DataMapper.update(new QueryObject(query, params));
	}

	private <T extends Entity> T ploadUniqueByQuery(final Class<T> entityClass, final QueryObject query) {
		log.fine("loadUniqueByQuery(" + query + ")");
		final IRowMapper<T> mapper = getRowMapper();
		return DataMapper.queryUnique(mapper, query);
	}

	private <T extends Entity> List<T> ploadByQuery(final Class<T> entityClass, final QueryObject query) {
		log.fine("loadByQuery(" + query + ")");
		final IRowMapper<T> mapper = getRowMapper();
		return DataMapper.query(mapper, query);
	}

	private <T extends Entity> List<T> ploadByColumn(final Class<T> entityClass, final String column,
			final Object value) {

		final IRowMapper<T> mapper = getRowMapper();

		if (!SingletonFactory.queryIsDefined(getClass(), "ploadByColumn")) {
			final MutableQueryObject query = new MutableQueryObject(new QueryObject()
			/**/.append("SELECT * FROM ")
			/**/.append(getTableName())
			/**/.append(" WHERE ")
			/**/.append("?="));
			SingletonFactory.querySet(getClass(), "ploadByColumn", query);
		}
		final IQueryObject query = SingletonFactory.queryGet(getClass(), "ploadByColumn").mutate(column,
				value);

		return DataMapper.query(mapper, query);
	}

	private <T extends Entity> T ploadById(final Class<T> entityClass, final Object id) {
		log.fine("loadById(" + id + ")");

		if (!SingletonFactory.queryIsDefined(getClass(), "ploadById")) {
			final MutableQueryObject query = new MutableQueryObject(new QueryObject()
			/**/.append("SELECT * FROM ")
			/**/.append(getTableName())
			/**/.append(" WHERE ")
			/**/.append("?=")
			/**/.append(getIdField().getColumnName()));
			SingletonFactory.querySet(getClass(), "ploadById", query);
		}
		final IQueryObject query = SingletonFactory.queryGet(getClass(), "ploadById").mutateParams(id);

		final IRowMapper<T> mapper = getRowMapper();
		return DataMapper.queryUnique(mapper, query);
	}

	private <T extends Entity> List<T> ploadBy(final Class<T> entityClass, final Criterion... criterions) {
		log.fine("loadBy(Criterion[])");

		final Criterion cs;
		if (criterions.length == 0) {
			// throw new
			// OrmException("this method requires almost one Criterion parameter");
			cs = Criteria.all();
		} else {
			if (criterions.length == 1) {
				cs = criterions[0];
			} else {
				cs = Criteria.concate(criterions);
			}
		}
		final IQueryObject query = new QueryObject()
		/**/.append("SELECT * FROM ")
		/**/.append(getTableName())
		/**/.append(cs.renderQuery())
		/**/;
		final IRowMapper<T> mapper = getRowMapper();
		return DataMapper.query(mapper, query);
	}

	private <T extends Entity> T ploadUniqueBy(final Class<T> entityClass, final Criterion... criterions) {
		log.fine("loadBy(Criterion[])");

		final Criterion cs;
		if (criterions.length == 0) {
			throw new OrmException("this method requires almost one Criterion parameter");
		} else {
			if (criterions.length == 1) {
				cs = criterions[0];
			} else {
				cs = Criteria.concate(criterions);
			}
		}
		final IQueryObject query = new QueryObject()
		/**/.append("SELECT * FROM ")
		/**/.append(getTableName())
		/**/.append(cs.renderQuery())
		/**/;
		final IRowMapper<T> mapper = getRowMapper();
		return DataMapper.queryUnique(mapper, query);
	}

	private <T extends Entity> List<T> ploadAll(final Class<T> entityClass) {
		log.fine("loadAll()");
		final IQueryObject query = new QueryObject()
		/**/.append("SELECT * FROM ")
		/**/.append(getTableName())
		/**/;
		final IRowMapper<T> mapper = getRowMapper();
		return DataMapper.query(mapper, query);
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
		SessionFactory.getSession().getIdentityMap().store(this);

		for (final OneToMany<?, ?> c : getOneToManies().values()) {
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

		log.fine("insert()");
		if (getIdField().generateBefore()) {
			getIdField().assignGeneratedValue();
		}

		if (!SingletonFactory.queryIsDefined(getClass(), "insert")) {
			final MutableQueryObject q = new MutableQueryObject(new QueryObject()
			/**/.append("INSERT INTO ")
			/**/.append(getTableName())
			/**/.append(" (")
			/**/.append(QueryGenUtils.columnNamesJoin(getFields()))
			/**/.append(") VALUES (")
			/**/.append(QueryGenUtils.parametersJoin(getFields()))
			/**/.append(")"));
			SingletonFactory.querySet(getClass(), "insert", q);
		}

		final IQueryObject query = SingletonFactory.queryGet(getClass(), "insert").mutateParams(
				QueryGenUtils.fieldValues(getFields()));

		DataMapper.update(query);

		if (!getIdField().generateBefore()) {
			getIdField().assignGeneratedValue();
		}
	}

	private void update() {
		log.fine("update()");

		if (!SingletonFactory.queryIsDefined(getClass(), "update")) {
			final MutableQueryObject query = new MutableQueryObject(new QueryObject()
			/**/.append("UPDATE ")
			/**/.append(getTableName())
			/**/.append(" SET ")
			/**/.append(QueryGenUtils.setColumnNamesExceptId(getIdField(), getFields()))
			/**/.append(" WHERE ")
			/**/.append(getIdField().getColumnName())
			/**/.append("=?"));
			SingletonFactory.querySet(getClass(), "update", query);
		}
		final IQueryObject query = SingletonFactory.queryGet(getClass(), "update").mutateParams(
				QueryGenUtils.fieldValuesIdLast(getIdField(), getFields()));

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
		log.fine("delete()");
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
		log.fine("delete(Criterion[])");
		final IQueryObject query = new QueryObject()
		/**/.append("DELETE FROM ")
		/**/.append(getTableName())
		/**/.append(criterion.renderQuery())
		/**/;
		DataMapper.update(query);
	}

	public static <T extends Entity> Long count(final Class<T> c, final Criterion criterion) {
		// log.fine("count(Criterion[])");
		final IQueryObject query = new QueryObject()
		/**/.append("SELECT COUNT(*) FROM ")
		/**/.append(SingletonFactory.getEntity(c).getTableName())
		/**/.append(criterion.renderQuery())
		/**/;
		return DataMapper.aggregate(query).longValue();
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> IRowMapper<T> getRowMapper() {
		return (IRowMapper<T>) SingletonFactory.getEntityMapper(getClass());
	}

}
