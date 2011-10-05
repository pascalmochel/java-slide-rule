package org.orm.criteria.dsl;

import org.orm.record.Entity;
import org.orm.record.field.Field;

import java.util.List;

public interface GetResult {

	<T extends Entity> T getUnique(final Class<T> entityClass);

	<T extends Entity> List<T> get(final Class<T> entityClass);

	<T> T getColumnValue(final Field<T> field);

}
