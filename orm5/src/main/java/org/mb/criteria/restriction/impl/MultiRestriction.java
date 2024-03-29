package org.mb.criteria.restriction.impl;

import org.mb.criteria.Criterion;
import org.mb.query.IQueryObject;
import org.mb.query.QueryObject;

public class MultiRestriction implements Criterion {

	protected final String op;
	protected final Criterion[] abstractRs;

	public MultiRestriction(final String op, final Criterion[] abstractRs) {
		super();
		this.op = op;
		this.abstractRs = abstractRs.clone();
	}

	public IQueryObject renderQuery() {
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
