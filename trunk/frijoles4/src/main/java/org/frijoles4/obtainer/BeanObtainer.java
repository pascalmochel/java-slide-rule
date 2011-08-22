package org.frijoles4.obtainer;

import java.lang.reflect.Method;

import org.frijoles4.exception.FrijolesException;

public class BeanObtainer implements IBeanObtainer {

	protected final Object factory;
	protected final Method factoryMethod;

	public BeanObtainer(final Object factory, final Method factoryMethod) {
		super();
		this.factory = factory;
		this.factoryMethod = factoryMethod;
	}

	public Object obtain(final Object... args) {
		try {
			return factoryMethod.invoke(factory, args);
		} catch (final Exception e) {
			throw new FrijolesException("error invoking a method factory:" + factoryMethod.toString(), e);
		}
	}

}
