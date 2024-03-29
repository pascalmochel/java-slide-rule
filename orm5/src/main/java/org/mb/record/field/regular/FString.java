package org.mb.record.field.regular;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.mb.record.field.Field;
import org.mb.record.field.IdentifiableField;

public class FString extends IdentifiableField<String> {

	public FString(final String columnName) {
		super(columnName);
	}

	public Field<String> doClone() {
		return new FString(getColumnName());
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		setValue(rs.getString(getColumnName()));
	}

	@Override
	public void loadIdentity(final ResultSet rs) throws SQLException {
		setValue(rs.getString(1));
	}

}
