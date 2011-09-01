package org.morm.criteria.impl;

import org.morm.criteria.Criterion;
import org.morm.record.QueryObject;

public class ColumnToValueRestriction extends AbstractColumnRestriction implements Criterion {

	protected final Object value;

	public ColumnToValueRestriction(final String column, final String op, final Object value) {
		super(column, op);
		this.value = value;
	}

	public QueryObject renderQuery() {
		return new QueryObject()
		/**/.append(column)
		/**/.append(op)
		/**/.append("?")
		/**/.addParams(value)
		/**/;
	}

	public Object getValue() {
		return value;
	}

}
