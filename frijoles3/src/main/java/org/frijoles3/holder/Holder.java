package org.frijoles3.holder;

import java.lang.reflect.Method;

public abstract class Holder {

	protected final String alias;
	protected final Object factoryProxy;
	protected final Object factoryObject;

	public Holder(final String alias, final Object factoryObject, final Object factoryProxy) {
		super();
		this.alias = alias;
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

	public String getAlias() {
		return alias;
	}

	public abstract Object getBean(final Method method);

}