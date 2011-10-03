package org.orm.criteria.aggregate;

import org.orm.record.field.Field;

public class Aggregator {

	protected final String query;

	public static Aggregator aggregate(final String function, final Field<?> field, final String alias) {
		return new Aggregator(function + "(" + field.getColumnName() + ") AS " + alias);
	}

	public static Aggregator field(final Field<?> field) {
		return new Aggregator(field.getColumnName());
	}

	public static Aggregator nul(final Field<?> field) {
		return new Aggregator("NULL AS " + field.getColumnName());
	}

	private Aggregator(final String query) {
		super();
		this.query = query;
	}

	public String render() {
		return query;
	}

}
