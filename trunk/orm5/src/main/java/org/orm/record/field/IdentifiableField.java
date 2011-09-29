package org.orm.record.field;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class IdentifiableField<T> extends Field<T> {

	public IdentifiableField(final String columnName) {
		super(columnName);
	}

	public abstract void loadIdentity(ResultSet rs) throws SQLException;

}
