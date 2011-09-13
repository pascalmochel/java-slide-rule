package org.morm.criteria.impl;

import org.morm.criteria.Criterion;
import org.morm.query.IQueryObject;
import org.morm.query.QueryObject;
import org.morm.record.field.Field;

public class OrderByField implements Criterion {

	protected String columnName;
	protected String order;

	protected OrderByField(String columnName, String order) {
		super();
		this.columnName = columnName;
		this.order = order;
	}

	public static OrderByField asc(Field<?> field) {
		return new OrderByField(field.getColumnName(), " ASC");
	}

	public static OrderByField desc(Field<?> field) {
		return new OrderByField(field.getColumnName(), " DESC");
	}

	public IQueryObject renderQuery() {
		return new QueryObject(columnName + order);
	}

}
