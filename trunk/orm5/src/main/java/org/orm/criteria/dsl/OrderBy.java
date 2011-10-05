package org.orm.criteria.dsl;

import org.orm.criteria.order.Order;

public interface OrderBy extends GetResult {
	GetResult orderBy(Order... orderByFields);
}