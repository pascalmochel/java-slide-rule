package org.morm.record.field.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FBigDecimal extends Field<BigDecimal> {

	public FBigDecimal(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final BigDecimal v = rs.getObject(getColumnName()) != null ? rs.getBigDecimal(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public void store(final PreparedStatement pstm, final int index) throws SQLException {
		pstm.setBigDecimal(index, getValue());
	}

	@Override
	public Field<BigDecimal> doClone() {
		return new FBigDecimal(getColumnName());
	}

	@Override
	public void loadAggregate(final ResultSet rs) throws SQLException {
		setValue(rs.getBigDecimal(1));
	}

}
