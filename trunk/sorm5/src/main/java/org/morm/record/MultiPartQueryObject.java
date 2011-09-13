package org.morm.record;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MultiPartQueryObject implements IQueryObject {

	// public static final MultiPartQueryObject VOID = new MultiPartQueryObject();

	protected final String query;
	protected String query2;
	protected final List<Object> params;

	public MultiPartQueryObject(final String query) {
		super();
		this.query = query;
		this.params = new LinkedList<Object>();
	}

	public MultiPartQueryObject set(String query2, final Object... params) {
		this.query2 = query2;
		this.params.clear();
		this.params.addAll(Arrays.asList(params));
		return this;
	}

	public MultiPartQueryObject set(IQueryObject queryObject) {
		return set(queryObject.getQuery(), queryObject.getParams());
	}

	public String getQuery() {
		return query + query2;
	}

	public Object[] getParams() {
		return params.toArray();
	}

	@Override
	public String toString() {
		return getQuery() + " -- " + params.toString();
	}

}
