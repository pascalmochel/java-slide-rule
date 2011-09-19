package org.morm.record.field;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Field<T> extends FieldDef<T> {

	protected T value;

	public Field(final String columnName) {
		super(columnName);
	}

	public T getValue() {
		return value;
	}

	public void setValue(final T value) {
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	public void setUncheckedValue(final Object value) {
		this.value = (T) value;
	}

	public abstract void load(ResultSet rs) throws SQLException;

	@Override
	public String toString() {
		return getColumnName() + "=" + getValue();
	}

}
