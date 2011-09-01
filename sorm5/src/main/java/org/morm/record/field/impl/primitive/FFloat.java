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
		final Float v = rs.getObject(getColumnName()) != null ? rs.getFloat(getColumnName()) : null;
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

	@Override
	public void loadAggregate(final ResultSet rs) throws SQLException {
		setValue(rs.getFloat(1));
	}

}