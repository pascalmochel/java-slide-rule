package org.morm.record.field.impl.primitive;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.field.Field;
import org.morm.record.field.IdentifiableField;

public class FShort extends IdentifiableField<Short> {

	public FShort(final String columnName) {
		super(columnName);
	}

	public Field<Short> doClone() {
		return new FShort(getColumnName());
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Short v = rs.getObject(getColumnName()) != null ? rs.getShort(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public void loadIdentity(final ResultSet rs) throws SQLException {
		final Short v = rs.getObject(1) != null ? rs.getShort(1) : null;
		setValue(v);
	}

}
