package org.orm.criteria.dsl;

import org.orm.record.Entity;
import org.orm.record.field.Field;

import java.util.List;

public interface GetResult {

	<T extends Entity> T uniqueResult(final Class<T> entityClass);

	<T extends Entity> T firstResult(final Class<T> entityClass);

	<T extends Entity> List<T> list(final Class<T> entityClass);

	<T> T getColumnValue(final Field<T> field);

}
