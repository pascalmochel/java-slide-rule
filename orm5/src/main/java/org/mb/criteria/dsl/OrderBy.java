package org.mb.criteria.dsl;

import org.mb.criteria.order.Order;

public interface OrderBy extends GetResult {
	GetResult orderBy(Order... orderByFields);
}