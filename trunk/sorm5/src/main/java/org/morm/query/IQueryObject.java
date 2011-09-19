package org.morm.query;

import java.util.List;

public interface IQueryObject {

	String getQuery();

	Object[] getParams();

	List<Object> getParamsList();

}
