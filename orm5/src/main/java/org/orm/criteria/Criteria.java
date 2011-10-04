package org.orm.criteria;

import org.orm.criteria.impl.Finish;
import org.orm.criteria.impl.OrderBy;
import org.orm.criteria.impl.Where;
import org.orm.criteria.order.Order;
import org.orm.mapper.DataMapper;
import org.orm.mapper.IRowMapper;
import org.orm.query.TableLastQueryObject;
import org.orm.record.Entity;
import org.orm.record.SingletonFactory;
import org.orm.record.field.Field;

import java.util.List;

public class Criteria implements Where, OrderBy {

	protected final TableLastQueryObject query = new TableLastQueryObject();

	public static <T extends Entity> Where select() {
		return new Criteria().innerSelect();
	}

	public static Finish query(final String query, final Object... params) {
		final Criteria r = new Criteria();
		r.query.addTableName();
		r.query.append(query);
		r.query.addParams(params);
		return r;
	}

	protected Criteria innerSelect() {
		query.append("SELECT * FROM ");
		query.addTableName();
		return this;
	}

	public OrderBy where(final Criterion criterion) {
		query.append(" WHERE ").append(criterion.renderQuery());
		return this;
	}

	public Finish orderBy(final Order... orderByFields) {
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

	public <T extends Entity> T getUnique(final Class<T> entityClass) {
		final T entity = SingletonFactory.getEntity(entityClass);
		this.query.setTableName(entity.getTableName());

		final IRowMapper<T> entityMapper = SingletonFactory.getEntityMapper(entityClass);
		return DataMapper.queryUnique(entityMapper, query);
	}

	public <T extends Entity> List<T> get(final Class<T> entityClass) {
		final T entity = SingletonFactory.getEntity(entityClass);
		this.query.setTableName(entity.getTableName());

		final IRowMapper<T> entityMapper = SingletonFactory.getEntityMapper(entityClass);
		return DataMapper.query(entityMapper, query);
	}

	public <T> T getColumnValue(final Field<T> field) {
		return DataMapper.aggregate(field, query);
	}

}
