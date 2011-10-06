package org.orm.criteria;

import org.orm.criteria.dsl.GetResult;
import org.orm.criteria.dsl.OrderBy;
import org.orm.criteria.order.Order;
import org.orm.mapper.DataMapper;
import org.orm.mapper.IRowMapper;
import org.orm.query.TableLastQueryObject;
import org.orm.record.Entity;
import org.orm.record.SingletonFactory;
import org.orm.record.field.Field;
import org.orm.record.field.FieldDef;

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
