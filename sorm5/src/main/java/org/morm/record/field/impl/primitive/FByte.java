package org.morm.record.field.impl.primitive;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;

public class FByte extends Field<Byte> {

	public FByte(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Byte v = rs.getObject(getColumnName()) != null ? rs.getByte(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public void store(final PreparedStatement pstm, final int index) throws SQLException {
		pstm.setByte(index, getValue());
	}

	@Override
	public Field<Byte> doClone() {
		return new FByte(getColumnName());
	}

	@Override
	public void loadAggregate(final ResultSet rs) throws SQLException {
		setValue(rs.getByte(1));
	}

}
