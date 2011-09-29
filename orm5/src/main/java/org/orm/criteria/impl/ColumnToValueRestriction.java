package org.orm.criteria.impl;

import org.orm.query.IQueryObject;
import org.orm.query.QueryObject;

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
