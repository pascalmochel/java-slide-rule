package org.mb.query;

import java.util.List;

public interface IQueryObject {

	public static final IQueryObject VOID = new QueryObject();

	String getQuery();

	List<Object> getParamsList();

}
