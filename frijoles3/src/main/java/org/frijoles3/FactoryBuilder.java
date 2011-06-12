package org.frijoles3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.frijoles3.anno.Scope;
import org.frijoles3.exception.FrijolesException;
import org.frijoles3.holder.AbstractHolder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * <ul>
 * <li>no has d'apendre una sintaxi (XML), la instanciació és pure java
 * <li>escrius una factoria, i f. gestiona el cicle de vida dels objectes
 * <li>=> menys reflect i més type-safe java
 * </ul>
 * 
 * @author mhoms
 */
public class FactoryBuilder implements InvocationHandler {

	protected final Object factoryObject;
	protected final Map<Method, AbstractHolder> beansMap;

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

	protected FactoryBuilder(final Object factoryObject) {
		super();
		this.beansMap = Collections.synchronizedMap(new HashMap<Method, AbstractHolder>());
		this.factoryObject = factoryObject;
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) {

		if (method.getName().equals("toString") && method.getParameterTypes().length == 0) {
			return factoryStateToString();
		}

		final Object[] callArguments = cons(proxy, args);

		Object resultingBean;
		final AbstractHolder abstractHolder = beansMap.get(method);
		if (abstractHolder != null) {

			resultingBean = abstractHolder.getBean(method, callArguments);
		} else {

			final Scope scope = method.getAnnotation(Scope.class);
			if (scope == null) {
				throw new FrijolesException("@Scope annotation not found in factory method: "
						+ method.toString());
			}

			final Class<? extends AbstractHolder> holderClass = scope.value();
			final AbstractHolder newAbstractHolder = AbstractHolder.buildHolder(holderClass,
					method.getName(), factoryObject, proxy);
			beansMap.put(method, newAbstractHolder);
			resultingBean = newAbstractHolder.getBean(method, callArguments);
		}

		return resultingBean;
	}

	protected Object factoryStateToString() {
		final Set<String> m = new TreeSet<String>();
		for (final AbstractHolder h : beansMap.values()) {
			m.add(h.toString());
		}
		return m.toString();
	}

	private static <T> T[] cons(final T e, final T[] ts) {
		if (ts != null && ts.length > 0) {
			ts[0] = e;
		}
		return ts;
	}

}