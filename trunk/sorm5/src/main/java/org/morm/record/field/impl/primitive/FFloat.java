package org.morm.record.field.impl.primitive;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;
import org.morm.record.field.IdentifiableField;

public class FFloat extends IdentifiableField<Float> {

	public FFloat(final String columnName) {
		super(columnName);
	}

	public Field<Float> doClone() {
		return new FFloat(getColumnName());
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Float v = rs.getObject(getColumnName()) != null ? rs.getFloat(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public void loadIdentity(final ResultSet rs) throws SQLException {
		final Float v = rs.getObject(1) != null ? rs.getFloat(1) : null;
		setValue(v);
	}

}
