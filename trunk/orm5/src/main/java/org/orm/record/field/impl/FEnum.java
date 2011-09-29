package org.orm.record.field.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.orm.record.field.Field;

public class FEnum<T extends Enum<T>> extends Field<String> {

	protected final Class<T> enumClass;

	public FEnum(final String columnName, Class<T> enumClass) {
		super(columnName);
		this.enumClass = enumClass;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(final String value) {
		this.value = value;
	}

	public T getEnumValue() {
		return Enum.valueOf(enumClass, value);
	}

	public void setEnumValue(T enume) {
		this.value = enume.name();
	}

	public Field<String> doClone() {
		return new FEnum<T>(getColumnName(), enumClass);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		setValue(rs.getString(getColumnName()));
	}

}
