package org.morm.record.field.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FBigDecimal extends Field<BigDecimal> {

	public FBigDecimal(final String columnName) {
		super(columnName);
	}

	@Override
	public Field<BigDecimal> doClone() {
		return new FBigDecimal(getColumnName());
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final BigDecimal v = rs.getObject(getColumnName()) == null ? null : rs.getBigDecimal(getColumnName());
		setValue(v);
	}

}