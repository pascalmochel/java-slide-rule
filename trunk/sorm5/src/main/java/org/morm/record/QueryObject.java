package org.morm.record;

import java.util.LinkedList;
import java.util.List;

public class QueryObject {

	protected final StringBuilder query;
	protected final List<Object> params;

	public QueryObject() {
		query = new StringBuilder();
		params = new LinkedList<Object>();
	}

	public QueryObject append(final String s) {
		query.append(s);
		return this;
	}

	public QueryObject addParams(final Object... os) {
		for (final Object o : os) {
			params.add(o);
		}
		return this;
	}

	public QueryObject append(final QueryObject query) {
		this.query.append(query.getQuery());
		for (final Object o : query.getParams()) {
			this.params.add(o);
		}
		return this;
	}

	public String getQuery() {
		return query.toString();
	}

	public Object[] getParams() {
		return params.toArray();
	}

	@Override
	public String toString() {
		return query + " -- " + params.toString();
	}

}
