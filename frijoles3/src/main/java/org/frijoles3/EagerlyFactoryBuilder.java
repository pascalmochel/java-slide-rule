package org.frijoles3;

import java.lang.reflect.Method;

import org.frijoles3.anno.Scope;
import org.frijoles3.exception.FrijolesException;
import org.frijoles3.holder.AbstractHolder;

import java.util.ArrayList;
import java.util.List;

public class EagerlyFactoryBuilder extends FactoryBuilder {

	@SuppressWarnings("unchecked")
	public static <T> T build(final Class<? extends T> factoryClassToProx) {

		try {
			if (factoryClassToProx.isInterface()) {
				throw new FrijolesException("factory must be a class, not an interface; offending object is "
						+ factoryClassToProx.toString());
			}

			final Object factoryObject = ProxyUtils.newInstanceOf(factoryClassToProx);
			return (T) ProxyUtils.buildDeproxableProxy(factoryObject,
					new EagerlyFactoryBuilder(factoryObject));
		} catch (final Exception e) {
			throw new FrijolesException("error building factory: " + factoryClassToProx.getSimpleName(), e);
		}
	}

	public static <T> T build(final Class<? extends T> factoryClassToProx, final Class<T> interfaceToCast) {
		return build(factoryClassToProx);
	}

	protected EagerlyFactoryBuilder(final Object factoryObject) {
		super(factoryObject);
		initializefactory();
	}

	protected final void initializefactory() {

		try {
			final List<Method> ms = new ArrayList<Method>();
			for (final Class<?> i : factoryObject.getClass().getInterfaces()) {
				for (final Method m : i.getMethods()) {
					ms.add(m);
				}
			}
			for (final Method m : ms) {
				final Scope scope = getScopeAnnotation(m);
				if (scope == null) {
					continue;
				}
				final AbstractHolder newAbstractHolder = AbstractHolder.buildHolder(scope.value(), m
						.getName(), factoryObject);
				beansMap.put(m, newAbstractHolder);
			}
		} catch (final Exception e) {
			throw new FrijolesException("error initializing early factory: "
					+ factoryObject.getClass().getSimpleName(), e);
		}
	}

}
