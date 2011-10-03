package org.orm.criteria.impl;

import org.orm.criteria.order.Order;

public interface OrderBy<T> extends Finish<T> {
	Finish<T> orderBy(Order... orderByFields);
}