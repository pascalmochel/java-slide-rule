package org.orm.criteria.impl;

import org.orm.criteria.Criterion;
import org.orm.query.IQueryObject;
import org.orm.query.QueryObject;
import org.orm.record.field.Field;

public class Order implements Criterion {

	protected String columnName;
	protected String order;

	protected Order(final String columnName, final String order) {
		super();
		this.columnName = columnName;
		this.order = order;
	}

	public static Order asc(final Field<?> field) {
		return new Order(field.getColumnName(), " ASC");
	}

	public static Order desc(final Field<?> field) {
		return new Order(field.getColumnName(), " DESC");
	}

	public IQueryObject renderQuery() {
		return new QueryObject(columnName + order);
	}

}
