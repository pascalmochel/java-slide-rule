package org.morm.record.field.impl.primitive;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FLong extends Field<Long> {

	public FLong(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Long v = rs.getObject(getColumnName()) != null ? rs.getLong(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public void store(final PreparedStatement pstm, final int index) throws SQLException {
		pstm.setLong(index, getValue());
	}

	@Override
	public Field<Long> doClone() {
		return new FLong(getColumnName());
	}

	@Override
	public void loadAggregate(final ResultSet rs) throws SQLException {
		setValue(rs.getLong(1));
	}

}
