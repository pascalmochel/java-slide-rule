package org.morm.record.field.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FInteger extends Field<Integer> {

	public FInteger(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		setValue(rs.getInt(getColumnName()));
	}

	@Override
	public void store(final PreparedStatement pstm, final int index) throws SQLException {
		pstm.setInt(index, getValue());
	}

	@Override
	public Field<Integer> doClone() {
		return new FInteger(getColumnName());
	}

}