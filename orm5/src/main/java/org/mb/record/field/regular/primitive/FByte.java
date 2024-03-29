package org.mb.record.field.regular.primitive;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.mb.record.field.Field;

public class FByte extends Field<Byte> {

	public FByte(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Byte v = rs.getObject(getColumnName()) != null ? rs.getByte(getColumnName()) : null;
		setValue(v);
	}

	public Field<Byte> doClone() {
		return new FByte(getColumnName());
	}

}
