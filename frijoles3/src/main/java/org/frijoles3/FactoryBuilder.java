package org.frijoles3;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.Holder;

import java.util.HashMap;
import java.util.Map;

public class FactoryBuilder implements InvocationHandler {

	protected final Object factoryObject;
	protected final Map<Method, Holder> beansMap;

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(final Class<? extends T> factoryClassToProx) {

		if (factoryClassToProx.isInterface()) {
			throw new RuntimeException("factory must be a class, not interface");
		}
		Object factoryObject;
		try {
			factoryObject = factoryClassToProx.newInstance();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

		return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), factoryClassToProx
				.getInterfaces(), new FactoryBuilder(factoryObject));
	}

	private FactoryBuilder(final Object factoryObject) {
		super();
		this.beansMap = new HashMap<Method, Holder>();
		this.factoryObject = factoryObject;
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		if (beansMap.containsKey(method)) {

			final Holder holder = beansMap.get(method);
			return holder.getBean(method);
		} else {

			final Scope scope = method.getAnnotation(Scope.class);
			if (scope == null) {
				throw new RuntimeException("annotation @Scope not found in factory method: "
						+ method.toString());
			}
			// System.out.println(method.getName());
			final Class<? extends Holder> holderClass = scope.value();
			final Holder holder;
			try {
				final Constructor<? extends Holder> c = holderClass
						.getConstructor(Object.class, Object.class);
				holder = c.newInstance(factoryObject, proxy);
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
			beansMap.put(method, holder);
			System.out.println("holding: " + method.getName());
			return holder.getBean(method);
		}
	}

	public Object getFactoryObject() {
		return factoryObject;
	}

}