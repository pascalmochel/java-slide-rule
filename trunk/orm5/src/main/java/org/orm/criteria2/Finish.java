package org.orm.criteria2;

import java.util.List;

import org.orm.query.IQueryObject;

public interface Finish<T> {

	IQueryObject renderQuery();
	T getUnique();
	List<T> get();
	
}
