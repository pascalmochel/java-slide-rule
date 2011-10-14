package org.mb.record.field.identity.impl;

import org.mb.query.IQueryObject;
import org.mb.record.field.IdentifiableField;
import org.mb.record.field.identity.IdentityGenerator;

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
		return IQueryObject.VOID;
	}

	@Override
	public IdentityGenerator<T> doClone() {
		return new NoKeyGenerator<T>((IdentifiableField<T>) field.doClone());
	}

}
