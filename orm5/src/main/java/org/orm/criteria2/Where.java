package org.orm.criteria2;

import org.orm.criteria.Criterion;

public interface Where extends Criterion {
	OrderBy where(Criterion criterion);
}