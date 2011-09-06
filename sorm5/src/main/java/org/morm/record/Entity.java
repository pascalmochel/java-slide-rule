package org.morm.record;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.criteria.Criteria;
import org.morm.criteria.Criterion;
import org.morm.exception.SormException;
import org.morm.mapper.DataMapper;
import org.morm.mapper.IRowMapper;
import org.morm.record.compo.ManyToOne;
import org.morm.record.compo.OneToMany;
import org.morm.record.field.Field;
import org.morm.record.field.FieldDef;
import org.morm.record.identity.IdentityGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

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
public class Entity {

	protected Logger LOG = Logger.getLogger(getClass().getName());

	private String tableName;

	private IdentityGenerator<?> idField;
	private final Map<String, Field<?>> fields;
	private final Set<ManyToOne<?, ?>> manyToOnes;
	private final Map<String, OneToMany<?, ?>> oneToManies;

	private final TableMapper mapper;

	public Entity() {
		super();
		this.tableName = getClass().getSimpleName().toUpperCase();
		this.fields = new LinkedHashMap<String, Field<?>>();
		this.manyToOnes = new HashSet<ManyToOne<?, ?>>();
		this.oneToManies = new HashMap<String, OneToMany<?, ?>>();

		this.mapper = new TableMapper(getClass());
	}

	protected void setTableName(final String tableName) {
		this.tableName = tableName;
	}

	protected void registerIdField(final IdentityGenerator<?> idField) {
		final IdentityGenerator<?> id = idField.doCloneId();
		this.idField = id;
		this.fields.put(id.getColumnName(), id);
	}

	protected void registerFields(final Field<?>... fs) {
		for (final Field<?> f : fs) {
			this.fields.put(f.getColumnName(), f.doClone());
		}
	}

	protected void registerManyToOne(final ManyToOne<?, ?> manyToOne) {
		final ManyToOne<?, ?> c = manyToOne.doCloneCollaboration();
		this.manyToOnes.add(c);
		this.fields.put(c.getColumnName(), c);
	}

	@SuppressWarnings("unchecked")
	protected <TID> void registerOneToMany(final OneToMany<TID, ?> oneToMany) {
		oneToMany.setSelfIdFieldRef((IdentityGenerator<TID>) this.idField);
		final OneToMany<TID, ?> c = oneToMany.doCloneCollaboration();
		this.oneToManies.put(c.getColumnName(), c);
	}

	@SuppressWarnings("unchecked")
	protected <T> void set(final FieldDef<T> fieldDef, final T value) {
		final Field<T> self = (Field<T>) fields.get(fieldDef.getColumnName());
		self.setValue(value);
	}

	@SuppressWarnings("unchecked")
	protected <T> T get(final FieldDef<T> fieldDef) {
		final Field<T> self = (Field<T>) fields.get(fieldDef.getColumnName());
		return self.getValue();
	}

	@SuppressWarnings("unchecked")
	protected <TID, E extends Entity> void setCollaboration(final ManyToOne<TID, E> manyToOneField,
			final E value) {
		final ManyToOne<TID, E> self = (ManyToOne<TID, E>) fields.get(manyToOneField.getColumnName());
		self.setCollaboration(value);
	}

	@SuppressWarnings("unchecked")
	protected <TID, E extends Entity> E getCollaboration(final ManyToOne<TID, E> manyToOneField) {
		final ManyToOne<TID, E> self = (ManyToOne<TID, E>) fields.get(manyToOneField.getColumnName());
		return self.getCollaboration();
	}

	@SuppressWarnings("unchecked")
	protected <E extends Entity> void setCollaborations(final OneToMany<?, E> collaborableField,
			final List<E> value) {
		final OneToMany<?, E> self = (OneToMany<?, E>) oneToManies.get(collaborableField.getColumnName());
		self.setCollaboration(value);
	}

	@SuppressWarnings("unchecked")
	protected <E extends Entity> List<E> getCollaborations(final OneToMany<?, E> collaborableField) {
		final OneToMany<?, E> self = (OneToMany<?, E>) oneToManies.get(collaborableField.getColumnName());
		return self.getCollaboration();
	}

	public List<Field<?>> getFields() {
		return new ArrayList<Field<?>>(fields.values());
	}

	@Override
	public String toString() {
		final List<String> r = new ArrayList<String>();
		for (final Field<?> i : fields.values()) {
			r.add(i.toString());
		}
		for (final OneToMany<?, ?> i : oneToManies.values()) {
			r.add(i.toString());
		}
		return r.toString();
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> T loadByQuery(final QueryObject query) {
		LOG.fine("loadByQuery(" + query + ")");
		return (T) DataMapper.queryUnique(mapper, query);
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> T loadById(final Object id) {
		LOG.fine("loadById(" + id + ")");
		final QueryObject query = new QueryObject()
		/**/.append("SELECT * FROM ")
		/**/.append(tableName)
		/**/.append(" WHERE ")
		/**/.append(idField.getColumnName())
		/**/.append("=?")
		/**/.addParams(id)
		/**/;
		return (T) DataMapper.queryUnique(mapper, query);
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> List<T> loadBy(final Criterion... criterions) {
		LOG.fine("loadBy(Criterion[])");
		final Criterion cs = Criteria.concate(criterions);
		final QueryObject query = new QueryObject()
		/**/.append("SELECT * FROM ")
		/**/.append(tableName)
		/**/.append(" WHERE ")
		/**/.append(cs.renderQuery())
		/**/;
		return (List<T>) DataMapper.query(mapper, query);
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> List<T> loadAll() {
		LOG.fine("loadAll()");
		final QueryObject query = new QueryObject()
		/**/.append("SELECT * FROM ")
		/**/.append(tableName)
		/**/;
		return (List<T>) DataMapper.query(mapper, query);
	}

	@SuppressWarnings("unchecked")
	public void store() {
		for (final ManyToOne<?, ?> c : manyToOnes) {
			if (c.getIsInit() && c.getValue() != null) {
				final Entity v = c.getCollaboration();
				v.store();
			}
		}

		if (idField.getValue() == null) {
			insert();
		} else {
			update();
		}

		for (final OneToMany<?, ?> c : oneToManies.values()) {
			if (c.getIsInit()) {
				final List<? extends Entity> cs = c.getCollaboration();
				if (cs != null) {
					for (final Entity e : cs) {
						e.set(((Field<Object>) c.getForeignField()), (Object) idField.getValue());
						e.store();
					}
				}
			}
		}
	}

	public void insert() {

		LOG.fine("insert()");
		if (idField.generateBefore()) {
			idField.assignGeneratedValue();
		}

		final QueryObject query = new QueryObject()
		/**/.append("INSERT INTO ")
		/**/.append(tableName)
		/**/.append(" (")
		/**/.append(QueryGenUtils.columnNamesJoin(fields.values()))
		/**/.append(") VALUES (")
		/**/.append(QueryGenUtils.parametersJoin(fields.values()))
		/**/.append(")")
		/**/.addParams(QueryGenUtils.fieldValues(fields.values()))
		/**/;
		DataMapper.update(query);

		if (!idField.generateBefore()) {
			idField.assignGeneratedValue();
		}
	}

	protected void update() {
		LOG.fine("update()");
		final QueryObject query = new QueryObject()
		/**/.append("UPDATE ")
		/**/.append(tableName)
		/**/.append(" SET ")
		/**/.append(QueryGenUtils.setColumnNamesExceptId(idField, fields.values()))
		/**/.append(" WHERE ")
		/**/.append(idField.getColumnName())
		/**/.append("=?")
		/**/.addParams(QueryGenUtils.fieldValuesIdLast(idField, fields.values()))
		/**/;
		DataMapper.update(query);
	}

	public void delete() {
		LOG.fine("delete()");
		final QueryObject query = new QueryObject()
		/**/.append("DELETE FROM ")
		/**/.append(tableName)
		/**/.append(" WHERE ")
		/**/.append(idField.getColumnName())
		/**/.append("=?")
		/**/.addParams(idField.getValue())
		/**/;
		DataMapper.update(query);
	}

	public void delete(final Criterion criterion) {
		LOG.fine("delete(Criterion[])");
		final QueryObject query = new QueryObject()
		/**/.append("DELETE FROM ")
		/**/.append(tableName)
		/**/.append(" WHERE ")
		/**/.append(criterion.renderQuery())
		/**/;
		DataMapper.update(query);
	}

	public Long count(final Criterion criterion) {
		LOG.fine("count(Criterion[])");
		final QueryObject query = new QueryObject()
		/**/.append("SELECT COUNT(*) FROM ")
		/**/.append(tableName)
		/**/.append(" WHERE ")
		/**/.append(criterion.renderQuery())
		/**/;
		return DataMapper.aggregate(query).longValue();
	}

	private static class TableMapper implements IRowMapper<Entity> {

		protected Class<? extends Entity> tableClass;

		public TableMapper(final Class<? extends Entity> tableClass) {
			this.tableClass = tableClass;
		}

		public Entity mapRow(final ResultSet rs) throws SQLException {
			try {
				final Entity r = tableClass.newInstance();

				for (final Field<?> f : r.getFields()) {
					try {
						f.load(rs);
					} catch (final Exception e) {
						throw new SormException("error mapping field: " + f.toString(), e);
					}
				}
				return r;
			} catch (final Exception e) {
				throw new SormException("error mapping " + getClass().getSimpleName(), e);
			}
		}
	}

	public String getTableName() {
		return tableName;
	}

	public IRowMapper<Entity> getRowMapper() {
		return mapper;
	}

	public IdentityGenerator<?> getIdField() {
		return idField;
	}

}
