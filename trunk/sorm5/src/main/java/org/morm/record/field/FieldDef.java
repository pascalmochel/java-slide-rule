package org.morm.record.field;

public abstract class FieldDef<T> {

	protected final String columnName;

	public FieldDef(final String columnName) {
		super();
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public abstract Field<T> doClone();

}
