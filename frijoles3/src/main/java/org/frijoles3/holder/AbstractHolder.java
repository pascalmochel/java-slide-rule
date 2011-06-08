package org.frijoles3.holder;

import java.lang.reflect.Method;

import org.frijoles3.exception.FrijolesException;

public abstract class AbstractHolder {

	protected final String alias;
	protected final Object factoryProxy;
	protected final Object factoryObject;

	public AbstractHolder(final String alias, final Object factoryObject, final Object factoryProxy) {
		super();
		this.alias = alias;
		this.factoryObject = factoryObject;
		this.factoryProxy = factoryProxy;
	}

	protected Object buildInstance(final Method method) {
		try {
			return method.invoke(factoryObject, factoryProxy);
		} catch (final Exception e) {
			throw new FrijolesException("building bean instance invoking: " + method.toString(), e);
		}
	}

	public String getAlias() {
		return alias;
	}

	public abstract Object getBean(final Method method);

}