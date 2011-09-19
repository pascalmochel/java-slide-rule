package org.morm.record.field.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FString extends Field<String> {

	public FString(final String columnName) {
		super(columnName);
	}

	public Field<String> doClone() {
		return new FString(getColumnName());
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		setValue(rs.getString(getColumnName()));
	}

}
