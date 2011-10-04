package org.orm.criteria;

import org.orm.criteria.impl.Finish;
import org.orm.criteria.impl.OrderBy;
import org.orm.criteria.impl.Where;
import org.orm.criteria.order.Order;
import org.orm.mapper.DataMapper;
import org.orm.query.IQueryObject;
import org.orm.query.QueryObject;
import org.orm.record.Entity;
import org.orm.record.SingletonFactory;
import org.orm.record.field.Field;

import java.util.List;

public class Criteria<T extends Entity> implements Where<T>, OrderBy<T> {

	protected Class<T> entityClass;
	protected final QueryObject query = new QueryObject();

	public static <T extends Entity> Where<T> select(final Class<T> entityClass) {
		return new Criteria<T>().innerSelect(entityClass);
	}

	public static <T extends Entity> Finish<T> query(String query, Object... params) {
		Criteria<T> r = new Criteria<T>();
		r.query.append(query);
		r.query.addParams(params);
		return r;
	}

	// public static <T extends Entity> Where<T> select(final Class<T>
	// entityClass,
	// final Aggregator... aggregators) {
	//
	// final Criteria<T> r = new Criteria<T>();
	//
	// r.query.append("SELECT ");
	// r.entityClass = entityClass;
	// final T entity = SingletonFactory.getEntity(entityClass);
	//
	// for (int i = 0; i < aggregators.length; i++) {
	// final Aggregator a = aggregators[i];
	// r.query.append(a.render());
	// if (i < aggregators.length - 1) {
	// r.query.append(",");
	// }
	// }
	// r.query.append(" FROM ").append(entity.getTableName());
	// return r;
	// }

	protected Criteria<T> innerSelect(final Class<T> entityClass) {
		this.entityClass = entityClass;
		final T entity = SingletonFactory.getEntity(entityClass);
		query.append("SELECT * FROM ").append(entity.getTableName());
		return this;
	}

	public OrderBy<T> where(final Criterion criterion) {
		query.append(" WHERE ").append(criterion.renderQuery());
		return this;
	}

	// public Having<T> groupBy(final Field<?>... fields) {
	// query.append(" GROUP BY ");
	// for (int i = 0; i < fields.length; i++) {
	// final Field<?> f = fields[i];
	// query.append(f.getColumnName());
	// if (i < fields.length - 1) {
	// query.append(",");
	// }
	// }
	// return this;
	// }
	//
	// public OrderBy<T> having(final Criterion criterion) {
	// query.append(" HAVING ").append(criterion.renderQuery());
	// return this;
	// }

	public Finish<T> orderBy(final Order... orderByFields) {
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

	public IQueryObject renderQuery() {
		return query;
	}

	public T getUnique() {
		return DataMapper.queryUnique(SingletonFactory.getEntityMapper(entityClass), query);
	}

	public List<T> get() {
		return DataMapper.query(SingletonFactory.getEntityMapper(entityClass), query);
	}

	public <T2> T2 getColumnValue(Field<T2> field) {
		return DataMapper.aggregate(field, query);
	}

}
