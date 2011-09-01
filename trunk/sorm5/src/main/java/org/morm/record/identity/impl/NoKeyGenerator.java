package org.morm.record.identity.impl;

import org.morm.record.identity.IdentityKeyGenerator;

public class NoKeyGenerator extends IdentityKeyGenerator {

	@Override
	public boolean generateBefore() {
		return true;
	}

	@Override
	public void getGeneratedValue() {
	}

	@Override
	public String getQuery() {
		return null;
	}

}
