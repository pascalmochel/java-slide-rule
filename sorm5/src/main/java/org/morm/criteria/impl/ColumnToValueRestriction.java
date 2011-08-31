package org.morm.criteria.impl;

import org.morm.criteria.interf.Criterion;
import org.morm.record.query.QueryObject;

public class ColumnToValueRestriction extends AbstractColumnRestriction implements Criterion {

	protected final Object value;

	public ColumnToValueRestriction(final String column, final String op, final Object value) {
		super(column, op);
		this.value = value;
	}

	public QueryObject renderSql() {
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
