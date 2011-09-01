package org.morm.record;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.criteria.Criteria;
import org.morm.criteria.Criterion;
import org.morm.exception.FrijolesException;
import org.morm.mapper.DataMapper;
import org.morm.mapper.IRowMapper;
import org.morm.record.field.Field;
import org.morm.record.field.FieldDef;
import org.morm.record.identity.IdentityKeyGenerator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Entity {

	private String tableName;

	private IdentityKeyGenerator<?> idField;
	private final Map<String, Field<?>> fields;

	private final TableMapper mapper;

	public Entity() {
		super();
		this.tableName = getClass().getSimpleName().toUpperCase();
		this.fields = new LinkedHashMap<String, Field<?>>();
		this.mapper = new TableMapper(getClass());
	}

	protected void setTableName(final String tableName) {
		this.tableName = tableName;
	}

	protected void registerIdField(final IdentityKeyGenerator<?> idField) {
		final IdentityKeyGenerator<?> id = idField.doCloneId();
		this.idField = id;
		this.fields.put(id.getColumnName(), id);
	}

	protected void registerFields(final Field<?>... fs) {
		for (final Field<?> f : fs) {
			this.fields.put(f.getColumnName(), f.doClone());
		}
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

	public List<Field<?>> getFields() {
		return new ArrayList<Field<?>>(fields.values());
	}

	@Override
	public String toString() {
		return fields.values().toString();
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> T loadById(final Object id) {
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
		final QueryObject query = new QueryObject()
		/**/.append("SELECT * FROM ")
		/**/.append(tableName)
		/**/;
		return (List<T>) DataMapper.query(mapper, query);
	}

	public void store() {
		if (idField.getValue() == null) {
			insert();
		} else {
			update();
		}
	}

	protected void insert() {

		if (idField.generateBefore()) {
			idField.setGeneratedValue();
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
			idField.setGeneratedValue();
		}
	}

	protected void update() {
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
		final QueryObject query = new QueryObject()
		/**/.append("DELETE FROM ")
		/**/.append(tableName)
		/**/.append(" WHERE ")
		/**/.append(criterion.renderQuery())
		/**/;
		DataMapper.update(query);
	}

	public Long count(final Criterion criterion) {
		final QueryObject query = new QueryObject()
		/**/.append("SELECT COUNT(*) FROM ")
		/**/.append(tableName)
		/**/.append(" WHERE ")
		/**/.append(criterion.renderQuery())
		/**/;
		return DataMapper.aggregate(query).longValue();
	}

	private class TableMapper implements IRowMapper<Entity> {

		protected Class<? extends Entity> tableClass;

		public TableMapper(final Class<? extends Entity> tableClass) {
			this.tableClass = tableClass;
		}

		public Entity mapRow(final ResultSet rs) throws SQLException {
			try {
				final Entity r = tableClass.newInstance();
				for (final Field<?> f : r.getFields()) {
					f.load(rs);
				}
				return r;
			} catch (final Exception e) {
				throw new FrijolesException(e);
			}
		}
	}

	public String getTableName() {
		return tableName;
	}

	public TableMapper getMapper() {
		return mapper;
	}

}
