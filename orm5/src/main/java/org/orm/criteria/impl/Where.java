package org.orm.criteria.impl;

import org.orm.criteria.Criterion;
import org.orm.criteria.order.Order;

public interface Where<T> extends GroupBy<T>, Finish<T> {
	GroupBy<T> where(Criterion criterion);

	Finish<T> orderBy(Order... orderByFields);
}