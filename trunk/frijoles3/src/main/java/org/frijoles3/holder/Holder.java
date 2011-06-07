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

	protected Object buildInstance(final Method method) {
		try {
			// System.out.println("building: " + method.getName());
			return method.invoke(factoryObject, factoryProxy);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public abstract Object getBean(final Method method);
}