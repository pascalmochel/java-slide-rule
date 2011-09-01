package org.morm.criteria.impl;

import org.morm.criteria.Criterion;
import org.morm.record.QueryObject;

public class MultiRestriction implements Criterion {

	protected final String op;
	protected final Criterion[] abstractRs;

	public MultiRestriction(final String op, final Criterion[] abstractRs) {
		super();
		this.op = op;
		this.abstractRs = abstractRs.clone();
	}

	public QueryObject renderQuery() {
		final QueryObject r = new QueryObject().append("(");
		for (int i = 0; i < abstractRs.length; i++) {
			r.append(abstractRs[i].renderQuery());
			if (i < abstractRs.length - 1) {
				r.append(op);
			}
		}
		r.append(")");
		return r;
	}

}