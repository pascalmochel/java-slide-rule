package org.morm.criteria.impl;

import org.morm.criteria.Criterion;
import org.morm.query.IQueryObject;
import org.morm.query.QueryObject;
import org.morm.record.field.Field;

public class Order implements Criterion {

	protected String columnName;
	protected String order;

	protected Order(String columnName, String order) {
		super();
		this.columnName = columnName;
		this.order = order;
	}

	public static Order asc(Field<?> field) {
		return new Order(field.getColumnName(), " ASC");
	}

	public static Order desc(Field<?> field) {
		return new Order(field.getColumnName(), " DESC");
	}

	public IQueryObject renderQuery() {
		return new QueryObject(columnName + order);
	}

}
