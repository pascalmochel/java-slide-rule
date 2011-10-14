package org.mb.criteria;

import org.mb.criteria.dsl.GetResult;
import org.mb.criteria.dsl.OrderBy;
import org.mb.criteria.order.Order;
import org.mb.mapper.DataMapper;
import org.mb.mapper.IRowMapper;
import org.mb.query.TableLastQueryObject;
import org.mb.record.Entity;
import org.mb.record.SingletonFactory;
import org.mb.record.field.Field;
import org.mb.record.field.FieldDef;

import java.util.List;

// per a un DSL collonut mirar: http://code.google.com/p/sql-dsl/
public class Criteria implements OrderBy {

	protected final TableLastQueryObject query = new TableLastQueryObject();

	public static GetResult query(final String query, final Object... params) {
		final Criteria r = new Criteria();
		r.query.addTableName();
		r.query.append(query);
		r.query.addParams(params);
		return r;
	}

	public static <T extends Entity> OrderBy selectBy(final Criterion criterion) {
		final Criteria r = new Criteria();
		r.query.append("SELECT * FROM ");
		r.query.addTableName();
		r.query.append(" WHERE ").append(criterion.renderQuery());
		return r;
	}

	public static <T extends Entity, F> OrderBy selectBy(final FieldDef<F> field, final F value) {
		final Criteria r = new Criteria();
		r.query.append("SELECT * FROM ");
		r.query.addTableName();
		r.query.append(" WHERE ?=").append(field.getColumnName()).addParams(value);
		return r;
	}

	public static <T extends Entity, F> T selectById(final Class<T> entityClass, final F value) {
		final T entity = SingletonFactory.getEntity(entityClass);
		final Criteria r = new Criteria();
		r.query.append("SELECT * FROM ");
		r.query.addTableName();
		r.query.append(" WHERE ?=").append(entity.getIdField().getColumnName()).addParams(value);
		return r.uniqueResult(entityClass);
	}

	public static <T extends Entity> OrderBy selectAll() {
		final Criteria r = new Criteria();
		r.query.append("SELECT * FROM ");
		r.query.addTableName();
		return r;
	}

	public GetResult orderBy(final Order... orderByFields) {
		query.append(" ORDER BY ");
		for (int i = 0; i < orderByFields.length; i++) {
			final Order o = orderByFields[i];
			query.append(o.renderQuery());
			if (i < orderByFields.length - 1) {
				query.append(",");
			}
		}
		return this;
	}

	/*
	 * carga
	 */

	public <T extends Entity> T uniqueResult(final Class<T> entityClass) {
		final T entity = SingletonFactory.getEntity(entityClass);
		this.query.setTableName(entity.getTableName());

		final IRowMapper<T> entityMapper = SingletonFactory.getEntityMapper(entityClass);
		return DataMapper.queryUnique(entityMapper, query);
	}

	public <T extends Entity> T firstResult(final Class<T> entityClass) {
		final T entity = SingletonFactory.getEntity(entityClass);
		this.query.setTableName(entity.getTableName());

		final IRowMapper<T> entityMapper = SingletonFactory.getEntityMapper(entityClass);
		return DataMapper.queryFirst(entityMapper, query);
	}

	public <T extends Entity> List<T> list(final Class<T> entityClass) {
		final T entity = SingletonFactory.getEntity(entityClass);
		this.query.setTableName(entity.getTableName());

		final IRowMapper<T> entityMapper = SingletonFactory.getEntityMapper(entityClass);
		return DataMapper.query(entityMapper, query);
	}

	public <T> T getColumnValue(final Field<T> field) {
		return DataMapper.aggregate(field, query);
	}

}
