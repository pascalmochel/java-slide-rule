package org.frijoles3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.frijoles3.anno.Scope;
import org.frijoles3.exception.FrijolesException;
import org.frijoles3.holder.AbstractHolder;
import org.frijoles3.web.FactoryWebLoader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 * <ul>
 * <li>no has d'apendre una sintaxi (XML), la instanciació és pure java
 * <li>escrius una factoria, i f. gestiona el cicle de vida dels objectes
 * <li>=> menys reflect i més type-safe java
 * </ul>
 * <p>
 * <ul>
 * <li>X configuració d'aplicació (exemples, awt, ...)
 * <li>X obtenció de resources: Properties, i18n
 * <li>X binding de formulari
 * <li>X transaction interception, dao dependencies, datasource...
 * </ul>
 * 
 * @author mhoms
 */
public class FactoryBuilder implements InvocationHandler, Deproxable {

	protected static final Logger LOG = Logger.getLogger(FactoryWebLoader.class.getName());

	protected final Object factoryObject;
	protected final Map<Method, AbstractHolder> beansMap;
	protected final Method deproxMethod;
	protected final Method toStringMethod;

	@SuppressWarnings("unchecked")
	public static <T> T build(final Class<T> factoryClassToProx) {

		if (factoryClassToProx.isInterface()) {
			throw new FrijolesException("factory must be a class, not an interface; offending object is "
					+ factoryClassToProx.toString());
		}

		final Object factoryObject = ProxyUtils.newInstanceOf(factoryClassToProx);
		return (T) ProxyUtils.buildProxy(factoryObject, new FactoryBuilder(factoryObject));
	}

	public static <T> T build(final Class<? extends T> factoryClassToProx, final Class<T> interfaceToCast) {
		return build(factoryClassToProx);
	}

	protected FactoryBuilder(final Object factoryObject) {
		super();

		this.beansMap = Collections.synchronizedMap(new HashMap<Method, AbstractHolder>());
		this.factoryObject = factoryObject;

		try {
			this.deproxMethod = Deproxable.class.getMethod(Deproxable.DEPROX_METHOD_NAME);
			this.toStringMethod = Object.class.getMethod("toString");
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

		LOG.info("factory " + factoryObject.getClass().getSimpleName() + " successfully builded");
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) {

		if (this.toStringMethod.equals(method)) {
			return toString();
		}
		if (this.deproxMethod.equals(method)) {
			return deprox();
		}

		final Object[] callArguments = setFirst(proxy, args);

		final Object resultingBean;
		final AbstractHolder abstractHolder = beansMap.get(method);
		if (abstractHolder == null) {

			// System.out.println("2 => " + method);
			final Scope scope = getScopeAnnotation(method);

			final AbstractHolder newAbstractHolder = AbstractHolder.buildHolder(scope.value(), method
					.getName(), factoryObject);

			beansMap.put(method, newAbstractHolder);
			resultingBean = newAbstractHolder.getBean(method, callArguments);
		} else {

			resultingBean = abstractHolder.getBean(method, callArguments);
		}

		return resultingBean;
	}

	protected Scope getScopeAnnotation(final Method method) {

		Scope scope = method.getAnnotation(Scope.class);
		if (scope != null) {
			return scope;
		}

		try {
			final Method m2 = factoryObject.getClass()
					.getMethod(method.getName(), method.getParameterTypes());
			scope = m2.getAnnotation(Scope.class);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		if (scope == null) {
			throw new FrijolesException("@Scope annotation not found in factory method: " + method.toString());
		}
		return scope;
	}

	@Override
	public String toString() {
		final Set<String> m = new TreeSet<String>();
		for (final AbstractHolder h : beansMap.values()) {
			m.add(h.toString());
		}
		return factoryObject.getClass().getSimpleName() + ": " + m.toString();
	}

	protected static <T> T[] setFirst(final T e, final T[] ts) {
		if (ts != null && ts.length > 0) {
			ts[0] = e;
		}
		return ts;
	}

	// public Map<Method, AbstractHolder> getBeansMap() {
	// return Collections.unmodifiableMap(beansMap);
	// }

	public Object deprox() {
		return this.factoryObject;
	}

}