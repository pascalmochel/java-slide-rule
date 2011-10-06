package org.orm.record.field.regular.primitive;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.orm.record.field.Field;
import org.orm.record.field.IdentifiableField;

public class FDouble extends IdentifiableField<Double> {

	public FDouble(final String columnName) {
		super(columnName);
	}

	public Field<Double> doClone() {
		return new FDouble(getColumnName());
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final Double v = rs.getObject(getColumnName()) != null ? rs.getDouble(getColumnName()) : null;
		setValue(v);
	}

	@Override
	public void loadIdentity(final ResultSet rs) throws SQLException {
		final Double v = rs.getObject(1) != null ? rs.getDouble(1) : null;
		setValue(v);
	}

}
