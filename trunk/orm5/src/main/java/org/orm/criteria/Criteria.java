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

import java.util.List;

public class Criteria<T extends Entity> implements Where<T>, OrderBy<T> {

	protected Class<T> entityClass;
	protected final QueryObject query = new QueryObject();

	public static <T extends Entity> Where<T> select(final Class<T> entityClass) {
		return new Criteria<T>().innerSelect(entityClass);
	}

	protected Where<T> innerSelect(final Class<T> entityClass) {
		this.entityClass = entityClass;
		T entity = SingletonFactory.getEntity(entityClass);
		query.append("SELECT * FROM ").append(entity.getTableName());
		return this;
	}

	public OrderBy<T> where(final Criterion criterion) {
		query.append(" WHERE ").append(criterion.renderQuery());
		return this;
	}

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

}
