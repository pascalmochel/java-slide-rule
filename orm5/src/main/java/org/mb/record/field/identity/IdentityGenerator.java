package org.mb.record.field.identity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.mb.query.IQueryObject;
import org.mb.record.field.Field;
import org.mb.record.field.IdentifiableField;

public abstract class IdentityGenerator<T> extends Field<T> {

	protected static final String PRODUCED_MORE_THAN_1_IDENTITY_KEY = "produced more than 1 identity key: ";
	protected static final String NO_IDENTITY_KEY_PRODUCED = "no identity key produced?: ";
	protected static final String ERROR_OBTAINING_IDENTITY_KEY = "error obtaining identity key: ";

	protected final IdentifiableField<T> field;
	protected IQueryObject query;

	public IdentityGenerator(final IdentifiableField<T> field) {
		super(field.getColumnName());
		this.field = field;
		this.query = null;
	}

	public void assignGeneratedValue() {
		if (this.query == null) {
			this.query = getQuery();
		}
		setGeneratedValue();
	}

	protected abstract void setGeneratedValue();

	protected abstract IQueryObject getQuery();

	public abstract boolean generateBefore();

	public abstract IdentityGenerator<T> doClone();

	@Override
	public T getValue() {
		return field.getValue();
	}

	@Override
	public void setValue(final T value) {
		field.setValue(value);
	}

	@Override
	public void load(final ResultSet rs) throws SQLException {
		field.load(rs);
	}

}
