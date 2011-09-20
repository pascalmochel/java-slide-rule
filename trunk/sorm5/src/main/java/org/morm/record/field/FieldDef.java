package org.morm.record.field;

public abstract class FieldDef<T> implements Clonable<Field<T>> {

	protected final String columnName;

	public FieldDef(final String columnName) {
		super();
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

}
