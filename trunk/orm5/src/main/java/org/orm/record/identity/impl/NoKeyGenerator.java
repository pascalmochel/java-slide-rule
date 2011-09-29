package org.orm.record.identity.impl;

import org.orm.query.IQueryObject;
import org.orm.query.QueryObject;
import org.orm.record.field.IdentifiableField;
import org.orm.record.identity.IdentityGenerator;

public class NoKeyGenerator<T> extends IdentityGenerator<T> {

	public NoKeyGenerator(final IdentifiableField<T> field) {
		super(field);
	}

	@Override
	public boolean generateBefore() {
		return false;
	}

	@Override
	public void setGeneratedValue() {
	}

	@Override
	public IQueryObject getQuery() {
		return QueryObject.VOID;
	}

	@Override
	public IdentityGenerator<T> doCloneId() {
		return new NoKeyGenerator<T>((IdentifiableField<T>) field.doClone());
	}

}
