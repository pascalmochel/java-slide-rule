package org.morm.record.field.impl.primitive;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FFloat extends Field<Float> {

	public FFloat(final String columnName) {
		super(columnName);
	}

 
	public void load(final ResultSet rs) throws SQLException {
		final Float v = rs.getObject(getColumnName()) != null ? rs.getFloat(getColumnName()) : null;
		setValue(v);
	}


	public Field<Float> doClone() {
		return new FFloat(getColumnName());
	}

}
