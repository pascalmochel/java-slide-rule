package org.mb.record.field.regular;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mb.record.field.Field;
import org.mb.record.field.IdentifiableField;

public class FBigDecimal extends IdentifiableField<BigDecimal> {

	public FBigDecimal(final String columnName) {
		super(columnName);
	}

	public Field<BigDecimal> doClone() {
		return new FBigDecimal(getColumnName());
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		final BigDecimal v = rs.getObject(getColumnName()) == null ? null : rs.getBigDecimal(getColumnName());
		setValue(v);
	}

	@Override
	public void loadIdentity(final ResultSet rs) throws SQLException {
		final BigDecimal v = rs.getObject(1) == null ? null : rs.getBigDecimal(1);
		setValue(v);
	}

}
