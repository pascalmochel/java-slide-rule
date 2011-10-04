package org.orm.criteria.impl;

import org.orm.criteria.Criterion;
import org.orm.criteria.order.Order;

public interface Where<T> extends Finish<T> {
	OrderBy<T> where(Criterion criterion);

	Finish<T> orderBy(Order... orderByFields);
}