package org.morm.record.field.impl.primitive;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FByte extends Field<Byte> {

	public FByte(final String columnName) {
		super(columnName);
	}

 
	public void load(final ResultSet rs) throws SQLException {
		final Byte v = rs.getObject(getColumnName()) != null ? rs.getByte(getColumnName()) : null;
		setValue(v);
	}


	public Field<Byte> doClone() {
		return new FByte(getColumnName());
	}

}
