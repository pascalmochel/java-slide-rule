package org.orm.query;

import org.orm.exception.OrmException;

public class TableLastQueryObject extends QueryObject {

	protected Integer tableIndex = null;

	public TableLastQueryObject addTableName() {
		this.tableIndex = super.query.length();
		return this;
	}

	public TableLastQueryObject setTableName(final String tableName) {
		if (this.tableIndex == null) {
			throw new OrmException("must call addTableName() before setTableName()");
		}
		super.query.insert(this.tableIndex, tableName);
		return this;
	}

}
