package org.morm.criteria;

import org.morm.record.query.QueryObject;

public interface Criterion {

	QueryObject renderSql();
}
