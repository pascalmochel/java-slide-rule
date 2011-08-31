package org.morm.criteria.interf;

import org.morm.record.query.QueryObject;

public interface Criterion {

	QueryObject renderSql();
}
