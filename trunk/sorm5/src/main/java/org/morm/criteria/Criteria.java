package org.morm.criteria;

import org.morm.criteria.impl.ColumnToValueRestriction;
import org.morm.criteria.impl.CriterionList;
import org.morm.criteria.impl.MultiRestriction;
import org.morm.criteria.impl.OrderBy;
import org.morm.record.QueryObject;
import org.morm.record.field.FieldDef;

public class Criteria {

	private Criteria() {

	}

	public static <T> Criterion eq(final FieldDef<T> field, final T value) {
		return new ColumnToValueRestriction(field.getColumnName(), "=", value);
	}

	public static <T> Criterion let(final FieldDef<T> field, final T value) {
		return new ColumnToValueRestriction(field.getColumnName(), "<=", value);
	}

	public static <T> Criterion get(final FieldDef<T> field, final T value) {
		return new ColumnToValueRestriction(field.getColumnName(), ">=", value);
	}

	public static <T> Criterion lt(final FieldDef<T> field, final T value) {
		return new ColumnToValueRestriction(field.getColumnName(), "<", value);
	}

	public static <T> Criterion gt(final FieldDef<T> field, final T value) {
		return new ColumnToValueRestriction(field.getColumnName(), ">", value);
	}

	public static <T> Criterion like(final FieldDef<T> field, final T value) {
		return new ColumnToValueRestriction(field.getColumnName(), " like ", value);
	}

//	public static <T> Criterion eq(final Entity entity1, final FieldDef<T> field1, final Entity entity2,
//			final FieldDef<T> field2) {
//		return new ColumnToColumnRestriction(entity1.getTableName(), field1.getColumnName(), "=", entity2
//				.getTableName(), field2.getColumnName());
//	}

	public static Criterion and(final Criterion... abstractRs) {
		return new MultiRestriction(" AND ", abstractRs);
	}

	public static Criterion or(final Criterion... abstractRs) {
		return new MultiRestriction(" OR ", abstractRs);
	}

	public static Criterion orderBy(final String byOrder, final FieldDef<?>... fields) {
		return new OrderBy(fields, byOrder);
	}

	public static Criterion concate(final Criterion[] criterions) {
		return new CriterionList(criterions);
	}

	public static Criterion all() {
		return new Criterion() {
			public QueryObject renderQuery() {
				return new QueryObject().append("1=1");
			}
		};
	}

}
