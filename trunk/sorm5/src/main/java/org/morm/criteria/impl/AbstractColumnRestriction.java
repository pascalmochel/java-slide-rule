package org.morm.criteria.impl;

import org.morm.criteria.interf.Value0;

public abstract class AbstractColumnRestriction implements Value0 {

	protected final String column;
	protected final String op;

	public AbstractColumnRestriction(final String column, final String op) {
		super();
		this.column = column;
		this.op = op;
	}

}
