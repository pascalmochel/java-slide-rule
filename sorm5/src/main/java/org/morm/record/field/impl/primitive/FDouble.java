package org.morm.record.field.impl.primitive;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FDouble extends Field<Double> {

	public FDouble(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Double v = rs.getObject(getColumnName()) != null ? rs.getDouble(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public Field<Double> doClone() {
		return new FDouble(getColumnName());
	}

}
