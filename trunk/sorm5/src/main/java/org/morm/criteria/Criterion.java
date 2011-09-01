package org.morm.criteria;

import org.morm.record.QueryObject;

public interface Criterion {

	QueryObject renderQuery();

}
