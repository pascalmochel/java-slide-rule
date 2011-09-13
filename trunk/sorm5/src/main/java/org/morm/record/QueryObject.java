package org.morm.record;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class QueryObject implements IQueryObject {

	public static final IQueryObject VOID = new QueryObject();

	protected final StringBuilder query;
	protected final List<Object> params;

	public QueryObject() {
		query = new StringBuilder();
		params = new LinkedList<Object>();
	}

	public QueryObject(final String query, final Object... params) {
		super();
		this.query = new StringBuilder().append(query);
		this.params = Arrays.asList(params);
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

	public IQueryObject setParams(final Object... params) {
		this.params.clear();
		this.params.addAll(Arrays.asList(params));
		return this;
	}

	public QueryObject append(final IQueryObject query) {
		this.query.append(query.getQuery());
		for (final Object o : query.getParams()) {
			this.params.add(o);
		}
		return this;
	}

	/* (non-Javadoc)
	 * @see org.morm.record.IQueryObject#getQuery()
	 */
	public String getQuery() {
		return query.toString();
	}

	/* (non-Javadoc)
	 * @see org.morm.record.IQueryObject#getParams()
	 */
	public Object[] getParams() {
		return params.toArray();
	}

	@Override
	public String toString() {
		return query + " -- " + params.toString();
	}

}
