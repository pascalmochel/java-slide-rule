package org.morm.record;

import org.morm.criteria.Criteria;
import org.morm.criteria.Criterion;
import org.morm.exception.SormException;
import org.morm.mapper.DataMapper;
import org.morm.mapper.EntityMapper;
import org.morm.mapper.IRowMapper;
import org.morm.record.compo.ManyToOne;
import org.morm.record.compo.OneToMany;
import org.morm.record.field.Field;

import java.util.List;

/**
 * <pre>
 * 
 * Dog 1-----* Rabbits
 * 
 * 
 * 
 * Dog.List&lt;Rabbit&gt; = SELECT * FROM RABBIT WHERE ID_DOG=$dog.idDog
 * 
 * 	tableNameOf(Rabbit.class)
 *  foreignOf(RABBIT).toPkOf(DOG)
 * 
 * 
 * Rabbit.Dog = SELECT 1 FROM DOG WHERE ID_DOG=rabbit.idDog
 * 
 * 	tableNameOf(Dog.class)
 *  pkOf(DOG).toFkOf(RABBIT)
 * 
 * 
 * </pre>
 */
public class Entity extends BaseEntity {

	private final EntityMapper entityMapper;

	public Entity() {
		super();
		this.entityMapper = new EntityMapper(getClass());
	}

	public static <T extends Entity> T loadById(final Class<T> entityClass, final Object id) {
		return SingletonFactory.get(entityClass).ploadById(entityClass, id);
	}

	public static <T extends Entity> List<T> loadBy(final Class<T> entityClass, final Criterion... criterions) {
		return SingletonFactory.get(entityClass).ploadBy(entityClass, criterions);
	}

	public static <T extends Entity> List<T> loadAll(final Class<T> entityClass) {
		return SingletonFactory.get(entityClass).ploadAll(entityClass);
	}

	public <T extends Entity> T loadUniqueByQuery(final Class<T> entityClass, final QueryObject query) {
		return SingletonFactory.get(entityClass).ploadUniqueByQuery(entityClass, query);
	}

	public <T extends Entity> List<T> loadByQuery(final Class<T> entityClass, final QueryObject query) {
		return SingletonFactory.get(entityClass).ploadByQuery(entityClass, query);
	}

	protected int sqlStatement(final Class<Entity> entityClass, final QueryObject query) {
		return SingletonFactory.get(entityClass).psqlStatement(query);
	}

	protected <T extends Entity> T ploadUniqueByQuery(final Class<T> entityClass, final QueryObject query) {
		log.fine("loadUniqueByQuery(" + query + ")");
		IRowMapper<T> mapper = getRowMapper();
		return DataMapper.queryUnique(mapper, query);
	}

	protected <T extends Entity> List<T> ploadByQuery(final Class<T> entityClass, final QueryObject query) {
		log.fine("loadByQuery(" + query + ")");
		IRowMapper<T> mapper = getRowMapper();
		return DataMapper.query(mapper, query);
	}

	protected int psqlStatement(final QueryObject query) {
		log.fine("sqlStatement()");
		return DataMapper.update(query);
	}

	protected <T extends Entity> T ploadById(final Class<T> entityClass, final Object id) {
		log.fine("loadById(" + id + ")");
		final QueryObject query = new QueryObject()
		/**/.append("SELECT * FROM ")
		/**/.append(getTableName())
		/**/.append(" WHERE ")
		/**/.append(getIdField().getColumnName())
		/**/.append("=?")
		/**/.addParams(id)
		/**/;
		IRowMapper<T> mapper = getRowMapper();
		return DataMapper.queryUnique(mapper, query);
	}

	protected <T extends Entity> List<T> ploadBy(final Class<T> entityClass, final Criterion... criterions) {
		log.fine("loadBy(Criterion[])");
		final Criterion cs = Criteria.concate(criterions);
		final QueryObject query = new QueryObject()
		/**/.append("SELECT * FROM ")
		/**/.append(getTableName())
		/**/.append(" WHERE ")
		/**/.append(cs.renderQuery())
		/**/;
		IRowMapper<T> mapper = getRowMapper();
		return DataMapper.query(mapper, query);
	}

	protected <T extends Entity> List<T> ploadAll(final Class<T> entityClass) {
		log.fine("loadAll()");
		final QueryObject query = new QueryObject()
		/**/.append("SELECT * FROM ")
		/**/.append(getTableName())
		/**/;
		IRowMapper<T> mapper = getRowMapper();
		return DataMapper.query(mapper, query);
	}

	@SuppressWarnings("unchecked")
	public void store() {
		for (final ManyToOne<?, ?> c : getManyToOnes()) {
			if (c.getIsInit()) {
				final Entity v = c.getCollaboration();
				v.store();
				set((Field<Object>) c, v.getIdField().getValue());
			}
		}

		if (getIdField().getValue() == null) {
			insert();
		} else {
			update();
		}

		for (final OneToMany<?, ?> c : getOneToManies().values()) {
			if (c.getIsInit()) {
				final List<? extends Entity> cs = c.getCollaboration();
				if (cs != null) {
					for (final Entity e : cs) {
						e.set(((Field<Object>) c.getForeignField()), getIdField().getValue());
						e.store();
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

		final QueryObject query = new QueryObject()
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

	protected void update() {
		log.fine("update()");
		final QueryObject query = new QueryObject()
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
			throw new SormException("updated more than 1 row");
		}
	}

	public void delete() {
		for (final ManyToOne<?, ?> c : getManyToOnes()) {
			final Entity collaboration = c.getCollaboration();
			if (collaboration != null) {
				collaboration.delete();
			}
		}
		log.fine("delete()");
		final QueryObject query = new QueryObject()
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
		log.fine("delete(Criterion[])");
		final QueryObject query = new QueryObject()
		/**/.append("DELETE FROM ")
		/**/.append(getTableName())
		/**/.append(" WHERE ")
		/**/.append(criterion.renderQuery())
		/**/;
		DataMapper.update(query);
	}

	public Long count(final Criterion criterion) {
		log.fine("count(Criterion[])");
		final QueryObject query = new QueryObject()
		/**/.append("SELECT COUNT(*) FROM ")
		/**/.append(getTableName())
		/**/.append(" WHERE ")
		/**/.append(criterion.renderQuery())
		/**/;
		return DataMapper.aggregate(query).longValue();
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> IRowMapper<T> getRowMapper() {
		return (IRowMapper<T>) entityMapper;
	}

}
