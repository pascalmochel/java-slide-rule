package org.frijoles3.holder.impl;

import java.lang.reflect.Method;

import org.frijoles3.holder.Holder;

public class Prototype extends Holder {

	public Prototype(final String alias, final Object factoryObject, final Object factoryProxy) {
		super(alias, factoryObject, factoryProxy);
	}

	@Override
	public Object getBean(final Method method) {
		return buildInstance(method);
	}

	@Override
	public String toString() {
		return getAlias() + "={prototype}";
	}

}