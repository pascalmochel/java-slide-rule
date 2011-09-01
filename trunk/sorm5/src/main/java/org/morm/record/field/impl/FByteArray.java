package org.morm.record.field.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FByteArray extends Field<byte[]> {

	public FByteArray(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		byte[] v = rs.getObject(getColumnName()) != null ? rs.getBytes(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public void store(final PreparedStatement pstm, final int index) throws SQLException {
		pstm.setBytes(index, getValue());
	}

	@Override
	public Field<byte[]> doClone() {
		return new FByteArray(getColumnName());
	}

}
