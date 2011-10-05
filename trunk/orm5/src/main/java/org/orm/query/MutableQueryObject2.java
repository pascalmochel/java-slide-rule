//package org.orm.query;
//
//import org.orm.exception.OrmException;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class MutableQueryObject2 implements IQueryObject {
//
//	protected final StringBuilder query;
//	protected final List<Object> params;
//	protected Integer tableIndex = null;
//
//	public MutableQueryObject2(final IQueryObject query) {
//		super();
//		this.query = new StringBuilder().append(query.getQuery());
//		this.params = query.getParamsList();
//	}
//
//	public MutableQueryObject2(final String query, final List<Object> params) {
//		super();
//		this.query = new StringBuilder().append(query);
//		this.params = params;
//	}
//
//	public MutableQueryObject2 addTableName() {
//		this.tableIndex = query.length();
//		return this;
//	}
//
//	public MutableQueryObject2 setTableName(final String tableName) {
//		if (this.tableIndex == null) {
//			throw new OrmException("must call addTableName() before setTableName()");
//		}
//		if (!this.tableIndex.equals(0)) {
//			query.insert(this.tableIndex, tableName);
//		}
//		return this;
//	}
//
//	public MutableQueryObject2 mutate(final String query, final Object... params) {
//		return new MutableQueryObject2(this.query + query, new ArrayList<Object>(Arrays.asList(params)));
//	}
//
//	// public MutableQueryObject2 mutateParams(final Object... params) {
//	// return new MutableQueryObject2(this.query, new
//	// ArrayList<Object>(Arrays.asList(params)));
//	// }
//
//	public String getQuery() {
//		return query.toString();
//	}
//
//	public List<Object> getParamsList() {
//		return params;
//	}
//
//	@Override
//	public String toString() {
//		return query + " -- " + params.toString();
//	}
//
// }
