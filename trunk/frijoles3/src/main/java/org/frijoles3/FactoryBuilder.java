package org.frijoles3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.frijoles3.anno.Scope;
import org.frijoles3.exception.FrijolesException;
import org.frijoles3.holder.AbstractHolder;
import org.frijoles3.web.FactoryWebLoader;

import java.util.Arrays;
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
 * <li>configuració d'aplicació
 * <li>obtenció de resources: Properties, i18n
 * <li>binding de formulari
 * <li>config de daos
 * </ul>
 * 
 * @author mhoms
 */
public class FactoryBuilder implements InvocationHandler, Deproxable {

	protected static final Logger LOG = Logger.getLogger(FactoryWebLoader.class.getName());

	protected final Object factoryObject;
	protected final Map<Method, AbstractHolder> beansMap;

	@SuppressWarnings("unchecked")
	public static <T> T build(final Class<? extends T> factoryClassToProx) {

		if (factoryClassToProx.isInterface()) {
			throw new FrijolesException("factory must be a class, not an interface; offending object is "
					+ factoryClassToProx.toString());
		}
		final Object factoryObject = newInstanceOf(factoryClassToProx);

		final Class<?>[] interfaces = cons(Deproxable.class, factoryClassToProx.getInterfaces());
		// final Class<?>[] interfaces = factoryClassToProx.getInterfaces();
		final T newProxyInstance = (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				interfaces, new FactoryBuilder(factoryObject));

		LOG.config("initializated factory: " + factoryClassToProx.getSimpleName());
		return newProxyInstance;
	}

	protected FactoryBuilder(final Object factoryObject) {
		super();

		this.beansMap = Collections.synchronizedMap(new HashMap<Method, AbstractHolder>());
		this.factoryObject = factoryObject;
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) {

		if (method.getName().equals("toString") && method.getParameterTypes().length == 0) {
			return toString();
		}
		if (method.getName().equals("deprox") && method.getParameterTypes().length == 0) {
			return deprox();
		}

		final Object[] callArguments = setFirst(proxy, args);

		Object resultingBean;
		final AbstractHolder abstractHolder = beansMap.get(method);
		if (abstractHolder == null) {

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
		} else {

			resultingBean = abstractHolder.getBean(method, callArguments);
		}

		return resultingBean;
	}

	@Override
	public String toString() {
		final Set<String> m = new TreeSet<String>();
		for (final AbstractHolder h : beansMap.values()) {
			m.add(h.toString());
		}
		return m.toString();
	}

	private static <T> T[] setFirst(final T e, final T[] ts) {
		if (ts != null && ts.length > 0) {
			ts[0] = e;
		}
		return ts;
	}

	/**
	 * add a new element at array ending
	 * <p>
	 * features a <tt>push(element)</tt>
	 */
	private static <T> T[] cons(final T element, final T[] array) {
		final T[] r = Arrays.copyOf(array, array.length + 1);
		r[array.length] = element;
		return r;
	}

	private static <T> T newInstanceOf(final Class<? extends T> claz) {
		T r;
		try {
			r = claz.newInstance();
		} catch (final Exception e) {
			throw new FrijolesException("cannot create " + claz.toString()
					+ ", it is visible, with a public default constructor?", e);
		}
		return r;
	}

	public Map<Method, AbstractHolder> getBeansMap() {
		return beansMap;
	}

	public Object deprox() {
		return this.factoryObject;
	}

}