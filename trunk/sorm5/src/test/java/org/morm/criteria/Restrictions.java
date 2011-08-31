package org.morm.criteria;

import org.morm.criteria.impl.ColumnToColumnRestriction;
import org.morm.criteria.impl.ColumnToValueRestriction;
import org.morm.criteria.impl.MultiRestriction;
import org.morm.criteria.interf.Value0;


public class Restrictions {

	public static Value0 eq(String column, Object value) {
		return new ColumnToValueRestriction(column, "=", value);
	}

	public static Value0 eq(String tableAlias1, String column1, String tableAlias2, String column2) {
		return new ColumnToColumnRestriction(tableAlias1, column1, "=", tableAlias2, column2);
	}

	public static Value0 and(Value0... abstractRs) {
		return new MultiRestriction(" AND ", abstractRs);
	}

	public static Value0 or(Value0... abstractRs) {
		return new MultiRestriction(" OR ", abstractRs);
	}

}
