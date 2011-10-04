package org.orm.criteria.impl;

import org.orm.criteria.Criterion;
import org.orm.criteria.order.Order;

public interface Where extends Finish {
	OrderBy where(Criterion criterion);

	Finish orderBy(final Order... orderByFields);
}