package org.morm.record.field.impl.date;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FDate extends Field<Date> {

	public FDate(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Date v = rs.getObject(getColumnName()) != null ? rs.getDate(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public void store(final PreparedStatement pstm, final int index) throws SQLException {
		pstm.setDate(index, getValue());
	}

	@Override
	public Field<Date> doClone() {
		return new FDate(getColumnName());
	}

	@Override
	public void loadAggregate(final ResultSet rs) throws SQLException {
		setValue(rs.getDate(1));
	}

}
