package org.orm.record.identity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.orm.exception.OrmException;
import org.orm.query.IQueryObject;
import org.orm.record.field.Field;
import org.orm.record.field.IdentifiableField;

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

	@Override
	public T getValue() {
		return field.getValue();
	}

	@Override
	public void setValue(final T value) {
		field.setValue(value);
	}

	public Field<T> doClone() {
		throw new OrmException("internal error, invoking " + getClass() + "#doClone()");
	}

	public abstract IdentityGenerator<T> doCloneId();

	@Override
	public void load(final ResultSet rs) throws SQLException {
		field.load(rs);
	}

}
