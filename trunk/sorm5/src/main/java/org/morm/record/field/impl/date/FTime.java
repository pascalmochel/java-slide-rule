package org.morm.record.field.impl.date;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import org.morm.record.field.Field;

public class FTime extends Field<Time> {

	public FTime(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Time v = rs.getObject(getColumnName()) != null ? rs.getTime(getColumnName()) : null;
		setValue(v);
	}

	public Field<Time> doClone() {
		return new FTime(getColumnName());
	}

}
