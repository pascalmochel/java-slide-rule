package org.morm.criteria;

import org.morm.criteria.impl.ColumnToColumnRestriction;
import org.morm.criteria.impl.ColumnToValueRestriction;
import org.morm.criteria.impl.MultiRestriction;
import org.morm.record.field.FieldDef;

public class Criteria {

	public static <T> Criterion eq(final FieldDef<T> field, final T value) {
		return new ColumnToValueRestriction(field.getColumnName(), "=", value);
	}

	public static <T> Criterion eq(final String tableAlias1, final FieldDef<T> field1,
			final String tableAlias2, final FieldDef<T> field2) {
		return new ColumnToColumnRestriction(tableAlias1, field1.getColumnName(), "=", tableAlias2, field2
				.getColumnName());
	}

	public static Criterion and(final Criterion... abstractRs) {
		return new MultiRestriction(" AND ", abstractRs);
	}

	public static Criterion or(final Criterion... abstractRs) {
		return new MultiRestriction(" OR ", abstractRs);
	}

}