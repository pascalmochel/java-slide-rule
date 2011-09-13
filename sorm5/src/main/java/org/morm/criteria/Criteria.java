package org.morm.criteria;

import org.morm.criteria.impl.ColumnToValueRestriction;
import org.morm.criteria.impl.MultiRestriction;
import org.morm.criteria.impl.OrderByField;
import org.morm.query.IQueryObject;
import org.morm.query.QueryObject;
import org.morm.record.QueryGenUtils;
import org.morm.record.field.FieldDef;

// TODO afegir limitacio/windowing de rows de select (ROWNUM,LIMIT,...) pero depen del dialecte
public class Criteria {

	public static final String ORDER_ASC = "ASC";
	public static final String ORDER_DESC = "DESC";

	private Criteria() {

	}

	public static <T> Criterion eq(final FieldDef<T> field, final T value) {
		return new ColumnToValueRestriction(field.getColumnName(), "=", value);
	}

	public static <T> Criterion ne(final FieldDef<T> field, final T value) {
		return new ColumnToValueRestriction(field.getColumnName(), "<>", value);
	}

	public static <T> Criterion le(final FieldDef<T> field, final T value) {
		return new ColumnToValueRestriction(field.getColumnName(), "<=", value);
	}

	public static <T> Criterion ge(final FieldDef<T> field, final T value) {
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

	public static <T> Criterion in(final FieldDef<T> field, final T... values) {
		return new Criterion() {
			public IQueryObject renderQuery() {
				return new QueryObject()
				/**/.append(field.getColumnName())
				/**/.append(" IN ")
				/**/.append("(")
				/**/.append(QueryGenUtils.join("?", ",", values.length))
				/**/.append(")")
				/**/.addParams(values);
			}
		};
	}

	public static <T> Criterion between(final FieldDef<T> field, final T value1, final T value2) {
		return new Criterion() {
			public IQueryObject renderQuery() {
				return new QueryObject()
				/**/.append(field.getColumnName())
				/**/.append(" BETWEEN ? AND ?")
				/**/.addParams(value1, value2);
			}
		};
	}

	public static <T> Criterion not(final Criterion criterion) {
		return new Criterion() {
			public IQueryObject renderQuery() {
				return new QueryObject()
				/**/.append("NOT(")
				/**/.append(criterion.renderQuery())
				/**/.append(")");
			}
		};
	}

	public static Criterion and(final Criterion... abstractRs) {
		return new MultiRestriction(" AND ", abstractRs);
	}

	public static Criterion or(final Criterion... abstractRs) {
		return new MultiRestriction(" OR ", abstractRs);
	}

	public static Criterion orderBy(final OrderByField... orderByFields) {
		return new Criterion() {
			public IQueryObject renderQuery() {
				final QueryObject r = new QueryObject(" ORDER BY ");
				for (int i = 0; i < orderByFields.length; i++) {
					final OrderByField o = orderByFields[i];
					r.append(o.renderQuery());
					if (i < orderByFields.length - 1) {
						r.append(",");
					}
				}
				return r;
			}
		};
	}

	public static Criterion concate(final Criterion[] criterions) {
		return new Criterion() {
			public IQueryObject renderQuery() {
				final QueryObject r = new QueryObject();
				for (final Criterion q : criterions) {
					r.append(q.renderQuery());
				}
				return r;
			}
		};
	}

	public static Criterion all() {
		return new Criterion() {
			public IQueryObject renderQuery() {
				return new QueryObject("1=1");
			}
		};
	}

	public static Criterion custom(final String sqlPart) {
		return new Criterion() {
			public IQueryObject renderQuery() {
				return new QueryObject(" " + sqlPart);
			}
		};
	}

}
