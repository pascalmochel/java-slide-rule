package org.morm.record.field.impl.date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.morm.record.field.Field;

public class FTimestamp extends Field<Timestamp> {

	public FTimestamp(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		Timestamp v = rs.getObject(getColumnName()) != null ? rs.getTimestamp(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public void store(final PreparedStatement pstm, final int index) throws SQLException {
		pstm.setTimestamp(index, getValue());
	}

	@Override
	public Field<Timestamp> doClone() {
		return new FTimestamp(getColumnName());
	}

}
