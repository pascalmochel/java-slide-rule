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

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		if (method.getName().equals("toString") && method.getParameterTypes().length == 0) {
			return factoryStateToString();
		}

		checkMethodSignature(method);

		final Object[] callArguments = cons(proxy, args);

		Object resultingBean;
		if (beansMap.containsKey(method)) {

			final AbstractHolder abstractHolder = beansMap.get(method);
			resultingBean = abstractHolder.getBean(method, callArguments);
		} else {

			final Scope scope = method.getAnnotation(Scope.class);
			if (scope == null) {
				throw new FrijolesException("@Scope annotation not found in factory method: "
						+ method.toString());
			}

			final Class<? extends AbstractHolder> holderClass = scope.value();
			final AbstractHolder abstractHolder = AbstractHolder.buildHolder(holderClass, method.getName(),
					factoryObject, proxy);
			beansMap.put(method, abstractHolder);
			resultingBean = abstractHolder.getBean(method, callArguments);
		}

		// System.out.println(Arrays.toString(proxy.getClass().getMethods()));

		// if (method.getAnnotation(InterceptBy.class) != null) {
		// final InterceptBy ia = method.getAnnotation(InterceptBy.class);
		// final String methodFactoryName = ia.value();
		// final Method[] ms = proxy.getClass().getMethods();
		// Method candidate = null;
		// for (final Method m : ms) {
		// if (m.getName().equals(methodFactoryName) && m.getReturnType() ==
		// void.class) {
		// candidate = m;
		// break;
		// }
		// }
		// if (candidate == null) {
		// throw new RuntimeException();
		// }
		// return candidate.invoke(proxy, new Object[] {});
		// }

		return resultingBean;
	}

	protected void checkMethodSignature(final Method method) {
		if (method.getReturnType() == void.class || method.getParameterTypes().length < 1) {
			throw new FrijolesException("this method factory has an invalid signature: " + method.toString());
		}
	}

	protected Object factoryStateToString() {
		final Set<String> m = new TreeSet<String>();
		for (final AbstractHolder h : beansMap.values()) {
			m.add(h.toString());
		}
		return m.toString();
	}

	public static <T> T[] cons(final T e, final T[] ts) {
		final T[] r = ts;
		r[0] = e;
		return ts;
	}

}