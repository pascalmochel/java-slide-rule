package org.morm.criteria.impl;

import org.morm.criteria.Criterion;
import org.morm.record.QueryGenUtils;
import org.morm.record.QueryObject;
import org.morm.record.field.FieldDef;

public class OrderBy implements Criterion {

	public static final String ASC = "ASC";
	public static final String DESC = "DESC";

	protected FieldDef<?>[] fields;
	protected String byOrder;

	public OrderBy(FieldDef<?>[] fields, String byOrder) {
		super();
		this.fields = fields.clone();
		this.byOrder = byOrder;
	}

	public QueryObject renderQuery() {
		return new QueryObject()
		/**/.append(" ORDER BY ")
		/**/.append(QueryGenUtils.columnNamesJoin(fields))
		/**/.append(" ")
		/**/.append(byOrder)
		/**/;
	}

}