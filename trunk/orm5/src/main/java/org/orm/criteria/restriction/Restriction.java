package org.orm.criteria.restriction;

import org.orm.criteria.Criterion;
import org.orm.criteria.order.Order;
import org.orm.criteria.restriction.impl.ColumnToValueRestriction;
import org.orm.criteria.restriction.impl.MultiRestriction;
import org.orm.query.IQueryObject;
import org.orm.query.QueryObject;
import org.orm.record.QueryGenUtils;
import org.orm.record.field.FieldDef;

public class Restriction {

	// TODO per a un DSL collonut mirar: http://code.google.com/p/sql-dsl/
	private Restriction() {
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
				/**/.append(" IN (")
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
				/**/.append("(")
				/**/.append(field.getColumnName())
				/**/.append(" BETWEEN ? AND ?)")
				/**/.addParams(value1, value2);
			}
		};
	}

	public static <T> Criterion isNull(final FieldDef<T> field) {
		return new Criterion() {
			public IQueryObject renderQuery() {
				return new QueryObject()
				/**/.append(field.getColumnName())
				/**/.append(" IS NULL");
			}
		};
	}

	public static <T> Criterion isNotNull(final FieldDef<T> field) {
		return new Criterion() {
			public IQueryObject renderQuery() {
				return new QueryObject()
				/**/.append(field.getColumnName())
				/**/.append(" IS NOT NULL");
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

	public static Criterion all() {
		return new Criterion() {
			public IQueryObject renderQuery() {
				return new QueryObject(" WHERE 1=1");
			}
		};
	}

	public static Criterion sqlClause(final String sqlPart) {
		return new Criterion() {
			public IQueryObject renderQuery() {
				return new QueryObject(sqlPart);
			}
		};
	}

	public static Criterion concate(final Criterion[] criterions) {
		return new Criterion() {
			public IQueryObject renderQuery() {
				final QueryObject r = new QueryObject();
				for (final Criterion c : criterions) {
					r.append(c.renderQuery());
				}
				return r;
			}
		};
	}

	public static Criterion where(final Criterion criterion) {
		return new Criterion() {
			public IQueryObject renderQuery() {
				final IQueryObject q = criterion.renderQuery();
				return new QueryObject(" WHERE ").append(q);
			}
		};
	}

	public static Criterion orderBy(final Order... orderByFields) {
		return new Criterion() {
			public IQueryObject renderQuery() {
				final QueryObject r = new QueryObject(" ORDER BY ");
				for (int i = 0; i < orderByFields.length; i++) {
					final Order o = orderByFields[i];
					r.append(o.renderQuery());
					if (i < orderByFields.length - 1) {
						r.append(",");
					}
				}
				return r;
			}
		};
	}

}