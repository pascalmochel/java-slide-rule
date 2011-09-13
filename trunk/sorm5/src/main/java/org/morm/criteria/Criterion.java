package org.morm.criteria;

import org.morm.record.IQueryObject;

public interface Criterion {

	IQueryObject renderQuery();

}
