package org.morm.criteria.impl;

import org.morm.criteria.interf.Value1;

public class ColumnToValueRestriction extends AbstractColumnRestriction implements Value1 {

	protected final Object value;

	public ColumnToValueRestriction(final String column, final String op, final Object value) {
		super(column, op);
		this.value = value;
	}

	public String renderSql() {
		return column + op + "?";
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return renderSql() + " -- " + getValue().toString();
	}

}
