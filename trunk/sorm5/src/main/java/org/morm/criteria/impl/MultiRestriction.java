package org.morm.criteria.impl;

import org.morm.criteria.interf.Value0;
import org.morm.criteria.interf.Value1;
import org.morm.criteria.interf.ValueN;

import java.util.ArrayList;
import java.util.List;

public class MultiRestriction implements ValueN {

	String op;
	Value0[] abstractRs;

	public MultiRestriction(final String op, final Value0[] abstractRs) {
		super();
		this.op = op;
		this.abstractRs = abstractRs;
	}

	public String renderSql() {
		final StringBuilder s = new StringBuilder().append('(');
		for (final Value0 r : abstractRs) {
			s.append(r.renderSql()).append(op);
		}
		s.delete(s.length() - op.length(), s.length()).append(')');
		return s.toString();
	}

	public List<Object> getValues() {
		final List<Object> r = new ArrayList<Object>();
		for (final Value0 a : abstractRs) {
			if (a instanceof Value1) {
				r.add(((Value1) a).getValue());
			} else if (a instanceof ValueN) {
				r.addAll(((ValueN) a).getValues());
			}
		}
		return r;
	}

	@Override
	public String toString() {
		return renderSql() + " -- " + getValues().toString();
	}

}
