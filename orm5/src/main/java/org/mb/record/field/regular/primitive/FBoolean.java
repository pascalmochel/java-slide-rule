package org.mb.record.field.regular.primitive;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.mb.record.field.Field;

public class FBoolean extends Field<Boolean> {

	public FBoolean(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Boolean v = rs.getObject(getColumnName()) != null ? rs.getBoolean(getColumnName()) : null;
		setValue(v);
	}

	public Field<Boolean> doClone() {
		return new FBoolean(getColumnName());
	}

}
