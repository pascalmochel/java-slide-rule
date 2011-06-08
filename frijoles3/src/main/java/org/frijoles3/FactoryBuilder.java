package org.frijoles3;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.frijoles3.anno.Scope;
import org.frijoles3.exception.FrijolesException;
import org.frijoles3.holder.Holder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class FactoryBuilder implements InvocationHandler {

	protected final Object factoryObject;
	protected final Map<Method, Holder> beansMap;

	@SuppressWarnings("unchecked")
	public static <T> T build(final Class<? extends T> factoryClassToProx) {

		if (factoryClassToProx.isInterface()) {
			throw new FrijolesException("factory must be a class, not an interface; offending object is "
					+ factoryClassToProx.toString());
		}
		final Object factoryObject = ReflectUtils.newInstanceOf(factoryClassToProx);

		return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), factoryClassToProx
				.getInterfaces(), new FactoryBuilder(factoryObject));
	}

	private FactoryBuilder(final Object factoryObject) {
		super();
		this.beansMap = Collections.synchronizedMap(new HashMap<Method, Holder>());
		this.factoryObject = factoryObject;
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		if (method.getName().equals("toString") && method.getParameterTypes().length == 0) {
			return factoryStateToString();
		}

		Object resultingBean;
		if (beansMap.containsKey(method)) {

			final Holder holder = beansMap.get(method);
			resultingBean = holder.getBean(method);
		} else {

			final Scope scope = method.getAnnotation(Scope.class);
			if (scope == null) {
				throw new FrijolesException("@Scope annotation not found in factory method: "
						+ method.toString());
			}

			final Class<? extends Holder> holderClass = scope.value();
			final Constructor<? extends Holder> constructor = ReflectUtils.holderConstructor(holderClass);
			final Holder holder = ReflectUtils.constructHolder(constructor, method.getName(), factoryObject,
					proxy);
			beansMap.put(method, holder);
			resultingBean = holder.getBean(method);
		}

		return resultingBean;
	}

	protected Object factoryStateToString() {
		final Set<String> m = new TreeSet<String>();
		for (final Holder h : beansMap.values()) {
			m.add(h.toString());
		}
		return m.toString();
	}

}