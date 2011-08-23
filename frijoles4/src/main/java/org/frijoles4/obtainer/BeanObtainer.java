package org.frijoles4.obtainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.frijoles4.exception.FrijolesException;
import org.frijoles4.exception.ThrowableRenderer;

public class BeanObtainer implements IBeanObtainer {

	protected final Object factoryObject;
	protected final Method factoryMethod;

	public BeanObtainer(final Object factoryObject, final Method factoryMethod) {
		super();
		this.factoryObject = factoryObject;
		this.factoryMethod = factoryMethod;
	}

	public Object obtain(final Object... args) {
		try {
			return factoryMethod.invoke(factoryObject, args);
		} catch (final InvocationTargetException e) {
			throw new FrijolesException("error invoking a method context: "
					+ ThrowableRenderer.renderFactoryMethodInfo(factoryMethod), e.getCause());
		} catch (final Exception e) {
			throw new FrijolesException("error invoking a method context: "
					+ ThrowableRenderer.renderFactoryMethodInfo(factoryMethod), e);
		}
	}

	public Object getFactoryObject() {
		return factoryObject;
	}

	public Method getFactoryMethod() {
		return factoryMethod;
	}

}
