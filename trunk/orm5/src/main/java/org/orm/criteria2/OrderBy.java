package org.orm.criteria2;

import org.orm.criteria.Criterion;
import org.orm.criteria.impl.Order;

public interface OrderBy extends Criterion {
	Criterion orderBy(Order... orderByFields);
}