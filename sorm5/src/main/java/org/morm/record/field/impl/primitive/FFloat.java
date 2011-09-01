package org.morm.record.field.impl.primitive;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FFloat extends Field<Float> {

	public FFloat(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		Float v = rs.getObject(getColumnName()) != null ? rs.getFloat(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public void store(final PreparedStatement pstm, final int index) throws SQLException {
		pstm.setFloat(index, getValue());
	}

	@Override
	public Field<Float> doClone() {
		return new FFloat(getColumnName());
	}

}