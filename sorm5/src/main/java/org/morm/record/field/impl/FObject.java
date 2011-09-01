package org.morm.record.field.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FObject extends Field<Object> {

	public FObject(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Object v = rs.getObject(getColumnName());
		setValue(v);
	}

	@Override
	public void store(final PreparedStatement pstm, final int index) throws SQLException {
		pstm.setObject(index, getValue());
	}

	@Override
	public Field<Object> doClone() {
		return new FObject(getColumnName());
	}

}
