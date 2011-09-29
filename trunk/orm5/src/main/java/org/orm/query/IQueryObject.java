package org.orm.query;

import java.util.List;

public interface IQueryObject {

	String getQuery();

	List<Object> getParamsList();

}
