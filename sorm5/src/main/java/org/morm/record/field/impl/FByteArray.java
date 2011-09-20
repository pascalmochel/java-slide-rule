package org.morm.record.field.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FByteArray extends Field<byte[]> {

	public FByteArray(final String columnName) {
		super(columnName);
	}

	public Field<byte[]> doClone() {
		return new FByteArray(getColumnName());
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final byte[] v = rs.getObject(getColumnName()) != null ? rs.getBytes(getColumnName()) : null;
		setValue(v);
	}

}
