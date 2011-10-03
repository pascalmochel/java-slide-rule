package org.orm.criteria2;

import org.orm.criteria.Criterion;
import org.orm.criteria.impl.Order;

public interface Where<T> extends Finish<T> {
	OrderBy<T> where(Criterion criterion);
	Finish<T> orderBy(Order... orderByFields);
}