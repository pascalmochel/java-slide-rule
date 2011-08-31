package org.morm.record;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.Utils;
import org.morm.criteria.interf.Value0;
import org.morm.mapper.DataMapper;
import org.morm.mapper.IRowMapper;
import org.morm.record.field.Field;
import org.morm.record.field.FieldDef;
import org.morm.record.query.QueryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static org.morm.criteria.FieldRestrictions.*;

public class Entity {

	protected final TableMapper mapper;

	protected Field<?> idField;
	protected final Map<String, Field<?>> fields;

	protected final String tableName;

	public Entity() {
		super();
		this.fields = new HashMap<String, Field<?>>();
		this.mapper = new TableMapper(getClass());
		this.tableName = getClass().getSimpleName().toUpperCase();
	}

	public void registerId(final Field<?> idField) {
		final Field<?> id = idField.doClone();
		this.idField = id;
		this.fields.put(id.getColumnName(), id);
	}

	public void register(final Field<?>... fs) {
		for (final Field<?> f : fs) {
			this.fields.put(f.getColumnName(), f.doClone());
		}
	}

	@SuppressWarnings("unchecked")
	public <T> void set(final FieldDef<T> fieldDef, final T value) {
		final Field<T> self = (Field<T>) fields.get(fieldDef.getColumnName());
		self.setValue(value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(final FieldDef<T> fieldDef) {
		final Field<T> self = (Field<T>) fields.get(fieldDef.getColumnName());
		return self.getValue();
	}

	// public static <T extends Entity> T of(final Class<T> entityClass) {
	// try {
	// return entityClass.newInstance();
	// } catch (final Exception e) {
	// throw new RuntimeException(e);
	// }
	// }

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
		return (T) DataMapper.queryUnique(mapper, query.getQuery(), query.getParams());
	}

	//
	// @SuppressWarnings("unchecked")
	// public <T extends Entity> T loadById(final Object id, final Value0
	// criterion) {
	// final QueryObject query = new QueryObject()
	// /**/.append("SELECT * FROM ")
	// /**/.append(tableName)
	// /**/.append(" WHERE ")
	// /**/.append(idField.getColumnName())
	// /**/.append("=?")
	// /**/.addParams(id)
	// /**/;
	// return (T) DataMapper.queryUnique(mapper, query.getQuery(),
	// query.getParams());
	// }

	@SuppressWarnings("unchecked")
	public <T extends Entity> List<T> loadAll() {
		final QueryObject query = new QueryObject()
		/**/.append("SELECT * FROM ")
		/**/.append(tableName)
		/**/;
		return (List<T>) DataMapper.query(mapper, query.getQuery(), query.getParams());
	}

	public void insert() {
		final QueryObject query = new QueryObject()
		/**/.append("INSERT INTO ")
		/**/.append(tableName)
		/**/.append(" (")
		/**/.append(Utils.columnNamesJoin(fields.values()))
		/**/.append(") VALUES (")
		/**/.append(Utils.parametersJoin(fields.values()))
		/**/.append(")")
		/**/.addParams(Utils.fieldValues(fields.values()))
		/**/;
		DataMapper.update(query.getQuery(), query.getParams());
	}

	public void update() {
		final QueryObject query = new QueryObject()
		/**/.append("UPDATE ")
		/**/.append(tableName)
		/**/.append(" SET ")
		/**/.append(Utils.setColumnNamesExceptId(idField, fields.values()))
		/**/.append(" WHERE ")
		/**/.append(idField.getColumnName())
		/**/.append("=?")
		/**/.addParams(Utils.fieldValuesIdLast(idField, fields.values()))
		/**/;
		DataMapper.update(query.getQuery(), query.getParams());
	}

	class TableMapper implements IRowMapper<Entity> {

		protected Class<? extends Entity> tableClass;

		public TableMapper(final Class<? extends Entity> tableClass) {
			this.tableClass = tableClass;
		}

		public Entity mapRow(final ResultSet rs) throws SQLException {
			try {
				final Entity r = EntityHolder.getInstance(tableClass);
				// final Entity r = Entity.of(tableClass);
				for (final Field<?> f : r.getFields()) {
					f.load(rs);
				}
				return r;
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}