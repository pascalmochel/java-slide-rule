package org.morm.record.field.impl.date;

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
		final Timestamp v = rs.getObject(getColumnName()) != null ? rs.getTimestamp(getColumnName()) : null;
		setValue(v);
	}


	public Field<Timestamp> doClone() {
		return new FTimestamp(getColumnName());
	}

}
