package org.morm.criteria;

import org.morm.criteria.impl.ColumnToColumnRestriction;
import org.morm.criteria.impl.ColumnToValueRestriction;
import org.morm.criteria.impl.MultiRestriction;
import org.morm.criteria.interf.Value0;
import org.morm.record.field.FieldDef;

public class FieldRestrictions {

	public static <T> Value0 eq(final FieldDef<T> field, final T value) {
		return new ColumnToValueRestriction(field.getColumnName(), "=", value);
	}

	public static <T> Value0 eq(final String tableAlias1, final FieldDef<T> field1, final String tableAlias2,
			final FieldDef<T> field2) {
		return new ColumnToColumnRestriction(tableAlias1, field1.getColumnName(), "=", tableAlias2, field2
				.getColumnName());
	}

	public static Value0 and(final Value0... abstractRs) {
		return new MultiRestriction(" AND ", abstractRs);
	}

	public static Value0 or(final Value0... abstractRs) {
		return new MultiRestriction(" OR ", abstractRs);
	}

}