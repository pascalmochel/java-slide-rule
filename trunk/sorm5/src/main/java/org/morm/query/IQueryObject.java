package org.morm.query;

import java.util.List;

public interface IQueryObject {

	public abstract String getQuery();

	public abstract Object[] getParams();
	public abstract List<Object> getParamsList();

}
