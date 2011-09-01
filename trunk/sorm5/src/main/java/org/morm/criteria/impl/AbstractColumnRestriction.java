package org.morm.criteria.impl;

import org.morm.criteria.Criterion;

public abstract class AbstractColumnRestriction implements Criterion {

	protected final String column;
	protected final String op;

	public AbstractColumnRestriction(final String column, final String op) {
		super();
		this.column = column;
		this.op = op;
	}

}
