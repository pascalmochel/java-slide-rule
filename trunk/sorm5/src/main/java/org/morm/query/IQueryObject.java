package org.morm.query;

import java.util.List;

public interface IQueryObject {

	String getQuery();

	List<Object> getParamsList();

}
