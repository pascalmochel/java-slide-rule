package org.orm.record.field.impl.primitive;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.orm.record.field.Field;
import org.orm.record.field.IdentifiableField;

public class FInteger extends IdentifiableField<Integer> {

	public FInteger(final String columnName) {
		super(columnName);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Integer v = rs.getObject(getColumnName()) != null ? rs.getInt(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public void loadIdentity(final ResultSet rs) throws SQLException {
		final Integer v = rs.getObject(1) != null ? rs.getInt(1) : null;
		setValue(v);
	}

	public Field<Integer> doClone() {
		return new FInteger(getColumnName());
	}

}
