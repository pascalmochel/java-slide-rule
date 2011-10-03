package org.orm.criteria.impl;

import org.orm.record.field.Field;

public interface GroupBy<T> extends OrderBy<T>, Finish<T> {

	Having<T> groupBy(Field<?>... fields);
}
