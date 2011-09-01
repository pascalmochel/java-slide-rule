package org.morm.record.identity.impl;

import org.morm.record.QueryObject;
import org.morm.record.field.Field;
import org.morm.record.identity.IdentityKeyGenerator;

public class NoKeyGenerator extends IdentityKeyGenerator {

	public NoKeyGenerator(final Field<?> fieldMeta) {
		super(fieldMeta);
	}

	@Override
	public boolean generateBefore() {
		return true;
	}

	@Override
	public void setGeneratedValue() {
	}

	@Override
	public QueryObject getQuery() {
		return null;
	}

}
