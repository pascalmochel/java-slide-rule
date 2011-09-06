package org.morm.record.identity.impl;

import org.morm.record.QueryObject;
import org.morm.record.field.Field;
import org.morm.record.identity.IdentityGenerator;

public class NoKeyGenerator<T> extends IdentityGenerator<T> {

	public NoKeyGenerator(final Field<T> field) {
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
	public QueryObject getQuery() {
		return QueryObject.VOID;
	}

	@Override
	public IdentityGenerator<T> doCloneId() {
		return new NoKeyGenerator<T>(field.doClone());
	}

}
