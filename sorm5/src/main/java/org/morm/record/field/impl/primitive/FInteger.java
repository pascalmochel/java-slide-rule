package org.morm.record.field.impl.primitive;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FInteger extends Field<Integer> {

	public FInteger(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Integer v = rs.getObject(getColumnName()) != null ? rs.getInt(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public Field<Integer> doClone() {
		return new FInteger(getColumnName());
	}

}
