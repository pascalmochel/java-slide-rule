package org.morm.record.field.impl.date;

import java.sql.PreparedStatement;
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
		Time v = rs.getObject(getColumnName()) != null ? rs.getTime(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public void store(final PreparedStatement pstm, final int index) throws SQLException {
		pstm.setTime(index, getValue());
	}

	@Override
	public Field<Time> doClone() {
		return new FTime(getColumnName());
	}

}
