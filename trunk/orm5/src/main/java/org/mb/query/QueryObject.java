package org.mb.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryObject implements IQueryObject {

	protected final StringBuilder query;
	protected final List<Object> params;

	public QueryObject() {
		query = new StringBuilder();
		params = new ArrayList<Object>();
	}

	public QueryObject(final String query, final Object... params) {
		super();
		this.query = new StringBuilder().append(query);
		this.params = new ArrayList<Object>(Arrays.asList(params));
	}

	public QueryObject append(final String s) {
		query.append(s);
		return this;
	}

	public QueryObject append(final IQueryObject q) {
		this.query.append(q.getQuery());
		params.addAll(q.getParamsList());
		return this;
	}

	public QueryObject addParams(final Object... os) {
		for (final Object o : os) {
			params.add(o);
		}
		return this;
	}

	public String getQuery() {
		return query.toString();
	}

	@Override
	public String toString() {
		return query + " -- " + params.toString();
	}

	public List<Object> getParamsList() {
		return this.params;
	}

}
