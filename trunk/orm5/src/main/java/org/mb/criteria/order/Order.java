package org.mb.criteria.order;

import org.mb.criteria.Criterion;
import org.mb.query.IQueryObject;
import org.mb.query.QueryObject;
import org.mb.record.field.Field;

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
