package org.morm.record.field.impl.primitive;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FShort extends Field<Short> {

	public FShort(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Short v = rs.getObject(getColumnName()) != null ? rs.getShort(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public Field<Short> doClone() {
		return new FShort(getColumnName());
	}

}
