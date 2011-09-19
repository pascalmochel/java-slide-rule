package org.morm.record.field.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FObject extends Field<Object> {

	public FObject(final String columnName) {
		super(columnName);
	}

	@Override
	public Field<Object> doClone() {
		return new FObject(getColumnName());
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Object v = rs.getObject(getColumnName());
		setValue(v);
	}

}
