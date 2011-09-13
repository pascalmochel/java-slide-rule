package org.morm.record.identity.impl;

import org.morm.query.IQueryObject;
import org.morm.query.QueryObject;
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
	public IQueryObject getQuery() {
		return QueryObject.VOID;
	}

	@Override
	public IdentityGenerator<T> doCloneId() {
		return new NoKeyGenerator<T>(field.doClone());
	}

}
