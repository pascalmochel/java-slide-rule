package org.morm.criteria.impl;

import org.morm.record.IQueryObject;
import org.morm.record.QueryObject;

public class ColumnToValueRestriction extends AbstractColumnRestriction {

	protected final Object value;

	public ColumnToValueRestriction(final String column, final String op, final Object value) {
		super(column, op);
		this.value = value;
	}

	public IQueryObject renderQuery() {
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
