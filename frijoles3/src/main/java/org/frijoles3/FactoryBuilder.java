package org.frijoles3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.frijoles3.anno.InterceptBy;
import org.frijoles3.anno.Scope;
import org.frijoles3.aop.Intercept;
import org.frijoles3.aop.Interceptor;
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

	private static final Object[] NULL_ARRAY = new Object[] { null };

	protected final Object factoryObject;
	protected final Map<Method, AbstractHolder> beansMap;
	protected final Map<Method, Interceptor> interceptorsSet;

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
		this.interceptorsSet = Collections.synchronizedMap(new HashMap<Method, Interceptor>());
		this.factoryObject = factoryObject;
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) {

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

		if (!interceptorsSet.containsKey(method)) {
			final InterceptBy ia = method.getAnnotation(InterceptBy.class);
			if (ia != null) {
				final String methodFactoryName = ia.value();
				final Method candidate = ReflectUtils.methodOf(proxy, methodFactoryName);

				Object interceptor;
				try {
					interceptor = candidate.invoke(proxy, NULL_ARRAY);
				} catch (final Exception e) {
					throw new FrijolesException(
							"interceptor factory method must have signature: Interceptor method(I)", e);
				}
				if (!(interceptor instanceof Interceptor)) {
					throw new FrijolesException("interceptor factory method must have to return a "
							+ Interceptor.class.getSimpleName() + ": " + methodFactoryName);
				}
				interceptorsSet.put(method, (Interceptor) interceptor);
			}
		}

		if (interceptorsSet.containsKey(method)) {
			return Intercept.with(resultingBean, interceptorsSet.get(method));
		}

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