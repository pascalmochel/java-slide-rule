package org.morm.criteria.impl;

public class ColumnToColumnRestriction extends AbstractColumnRestriction {

	protected final String aliasTable1;
	protected final String aliasTable2;
	protected final String column2;

	public ColumnToColumnRestriction(final String aliasTable1, final String column1, final String op,
			final String aliasTable2, final String column2) {
		super(column1, op);
		this.aliasTable1 = aliasTable1;
		this.aliasTable2 = aliasTable2;
		this.column2 = column2;
	}

	public String renderSql() {
		return aliasTable1 + "." + column + op + aliasTable2 + "." + column2;
	}

	@Override
	public String toString() {
		return renderSql();
	}

}
