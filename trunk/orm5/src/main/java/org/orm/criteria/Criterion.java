package org.orm.criteria;

import org.orm.query.IQueryObject;

public interface Criterion {

	IQueryObject renderQuery();

}
