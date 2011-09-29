package org.orm.criteria.impl;

import org.orm.criteria.Criterion;

public abstract class AbstractColumnRestriction implements Criterion {

	protected final String column;
	protected final String op;

	public AbstractColumnRestriction(final String column, final String op) {
		super();
		this.column = column;
		this.op = op;
	}

}
