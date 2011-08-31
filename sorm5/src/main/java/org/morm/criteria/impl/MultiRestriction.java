package org.morm.criteria.impl;

import org.morm.criteria.Criterion;
import org.morm.record.query.QueryObject;

public class MultiRestriction implements Criterion {

	String op;
	Criterion[] abstractRs;

	public MultiRestriction(final String op, final Criterion[] abstractRs) {
		super();
		this.op = op;
		this.abstractRs = abstractRs;
	}

	public QueryObject renderSql() {
		final QueryObject r = new QueryObject().append("(");
		for (int i = 0; i < abstractRs.length; i++) {
			r.append(abstractRs[i].renderSql());
			if (i < abstractRs.length - 1) {
				r.append(op);
			}
		}
		r.append(")");
		return r;
	}

	// public String renderSql() {
	// final StringBuilder s = new StringBuilder().append('(');
	// for (final Value0 r : abstractRs) {
	// s.append(r.renderSql()).append(op);
	// }
	// s.delete(s.length() - op.length(), s.length()).append(')');
	// return s.toString();
	// }
	//
	// public List<Object> getValues() {
	// final List<Object> r = new ArrayList<Object>();
	// for (final Value0 a : abstractRs) {
	// if (a instanceof Value1) {
	// r.add(((Value1) a).getValue());
	// } else if (a instanceof ValueN) {
	// r.addAll(((ValueN) a).getValues());
	// }
	// }
	// return r;
	// }
	//
	// @Override
	// public String toString() {
	// return renderSql() + " -- " + getValues().toString();
	// }

}
