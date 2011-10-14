package org.mb.record.field.regular.date;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mb.record.field.Field;

public class FDate extends Field<Date> {

	public FDate(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Date v = rs.getObject(getColumnName()) != null ? rs.getDate(getColumnName()) : null;
		setValue(v);
	}

	public Field<Date> doClone() {
		return new FDate(getColumnName());
	}

}
