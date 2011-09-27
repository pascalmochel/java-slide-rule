package org.morm.query;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MutableQueryObject implements IQueryObject {

	protected final String query;
	protected final List<Object> params;

	// public MutableQueryObject(final String query) {
	// super();
	// this.query = query;
	// this.params = new LinkedList<Object>();
	// }

	public MutableQueryObject(final IQueryObject query) {
		super();
		this.query = query.getQuery();
		this.params = query.getParamsList();
	}

	public MutableQueryObject(final String query, final List<Object> params) {
		super();
		this.query = query;
		this.params = params;
	}

	public MutableQueryObject mutate(final String query, final Object... params) {
		return new MutableQueryObject(this.query + query, new LinkedList<Object>(Arrays.asList(params)));
	}

	public MutableQueryObject mutateParams(final Object... params) {
		return new MutableQueryObject(this.query, new LinkedList<Object>(Arrays.asList(params)));
	}

	public String getQuery() {
		return query;
	}

	public Object[] getParams() {
		return params.toArray();
	}

	public List<Object> getParamsList() {
		return params;
	}

	@Override
	public String toString() {
		return query + " -- " + params.toString();
	}

}
