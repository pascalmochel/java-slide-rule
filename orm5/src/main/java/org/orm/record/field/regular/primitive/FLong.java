package org.orm.record.field.regular.primitive;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.orm.record.field.Field;
import org.orm.record.field.IdentifiableField;

public class FLong extends IdentifiableField<Long> {

	public FLong(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Long v = rs.getObject(getColumnName()) != null ? rs.getLong(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public void loadIdentity(final ResultSet rs) throws SQLException {
		final Long v = rs.getObject(1) != null ? rs.getLong(1) : null;
		setValue(v);
	}

	public Field<Long> doClone() {
		return new FLong(getColumnName());
	}

}
