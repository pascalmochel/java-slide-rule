package org.orm.criteria2;

import org.orm.criteria.Criterion;
import org.orm.criteria.impl.Order;
import org.orm.query.IQueryObject;
import org.orm.query.QueryObject;
import org.orm.record.Entity;
import org.orm.record.SingletonFactory;

public class Crit implements Select, OrderBy {

	protected Entity entity;
	protected final QueryObject q = new QueryObject();

	public Select select(final Class<? extends Entity> entityClass) {
		this.entity = SingletonFactory.getEntity(entityClass);
		q.append("SELECT * FROM " + entity.getTableName());
		return this;
	}

	@Override
	public OrderBy where(final Criterion criterion) {
		q.append(" WHERE ");
		q.append(criterion.renderQuery());
		return this;
	}

	@Override
	public Criterion orderBy(final Order... orderByFields) {
		q.append(" ORDER BY ");
		for (int i = 0; i < orderByFields.length; i++) {
			final Order o = orderByFields[i];
			q.append(o.renderQuery());
			if (i < orderByFields.length - 1) {
				q.append(",");
			}
		}
		return this;
	}

	@Override
	public IQueryObject renderQuery() {
		return q;
	}

}
