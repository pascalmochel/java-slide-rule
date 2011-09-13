//package org.morm.query;
//
//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.List;
//
//public class MultiPartQueryObject implements IQueryObject {
//
//	// public static final MultiPartQueryObject VOID = new
//	// MultiPartQueryObject();
//
//	protected final String query;
//	protected String query2;
//	protected final List<Object> params;
//
//	public MultiPartQueryObject(final String query) {
//		super();
//		this.query = query;
//		this.params = new LinkedList<Object>();
//	}
//
//	public MultiPartQueryObject setPart(final String query2) {
//		this.query2 = query2;
//		return this;
//	}
//
//	public MultiPartQueryObject setValues(final Object... params) {
//		this.params.clear();
//		this.params.addAll(Arrays.asList(params));
//		return this;
//	}
//
//	public MultiPartQueryObject set(final IQueryObject queryObject) {
//		setPart(queryObject.getQuery());
//		setValues(queryObject.getParams());
//		return this;
//	}
//
//	public String getQuery() {
//		return query + query2;
//	}
//
//	public Object[] getParams() {
//		return params.toArray();
//	}
//
//	@Override
//	public String toString() {
//		return getQuery() + " -- " + params.toString();
//	}
//
// }
