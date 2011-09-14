package org.morm.criteria.impl;

import org.morm.criteria.Criterion;
import org.morm.query.IQueryObject;
import org.morm.query.QueryObject;
import org.morm.record.field.Field;

public class OrderByField implements Criterion {

	protected String columnName;
	protected String order;

	protected OrderByField(final String columnName, final String order) {
		super();
		this.columnName = columnName;
		this.order = order;
	}

	public static OrderByField asc(final Field<?> field) {
		return new OrderByField(field.getColumnName(), " ASC");
	}

	public static OrderByField desc(final Field<?> field) {
		return new OrderByField(field.getColumnName(), " DESC");
	}

	public IQueryObject renderQuery() {
		return new QueryObject(columnName + order);
	}

}
