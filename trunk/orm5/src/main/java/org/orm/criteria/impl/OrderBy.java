package org.orm.criteria.impl;

import org.orm.criteria.order.Order;

public interface OrderBy extends Finish {
	Finish orderBy(Order... orderByFields);
}