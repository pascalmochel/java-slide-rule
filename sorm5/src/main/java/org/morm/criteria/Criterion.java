package org.morm.criteria;

import org.morm.query.IQueryObject;

public interface Criterion {

	IQueryObject renderQuery();

}
