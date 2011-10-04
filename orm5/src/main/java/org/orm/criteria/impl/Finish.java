package org.orm.criteria.impl;

import org.orm.criteria.Criterion;
import org.orm.record.field.Field;

import java.util.List;

public interface Finish<T> extends Criterion {

	T getUnique();

	List<T> get();

	<T2> T2 getColumnValue(Field<T2> field);
}
