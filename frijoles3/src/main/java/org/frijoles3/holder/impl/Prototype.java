package org.frijoles3.holder.impl;

import java.lang.reflect.Method;

import org.frijoles3.holder.AbstractHolder;

public class Prototype extends AbstractHolder {

	public Prototype(final String alias, final Object factoryObject) {
		super(alias, factoryObject);
	}

	@Override
	public Object getBean(final Method method, final Object[] extraParameters) {
		return buildInstance(method, extraParameters);
	}

	@Override
	public String toString() {
		return getAlias() + "={prototype}";
	}

}