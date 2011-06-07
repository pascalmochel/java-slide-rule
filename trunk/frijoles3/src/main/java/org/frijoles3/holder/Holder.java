package org.frijoles3.holder;

import java.lang.reflect.Method;

public abstract class Holder {

	protected final Object factoryProxy;
	protected final Object factoryObject;

	public Holder(final Object factoryObject, final Object factoryProxy) {
		super();
		this.factoryObject = factoryObject;
		this.factoryProxy = factoryProxy;
	}

	public abstract Object getBean(final Method method);
}