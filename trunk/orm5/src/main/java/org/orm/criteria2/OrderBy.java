package org.orm.criteria2;

import org.orm.criteria.impl.Order;

public interface OrderBy<T> extends Finish<T> {
	Finish<T> orderBy(Order... orderByFields);
}