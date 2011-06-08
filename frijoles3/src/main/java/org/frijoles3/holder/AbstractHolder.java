package org.frijoles3.holder;

import java.lang.reflect.Method;

import org.frijoles3.exception.FrijolesException;

public abstract class AbstractHolder {

	protected final String alias;
	protected final Object factoryProxy;
	protected final Object factoryObject;

	public AbstractHolder(final String alias, final Object factoryObject, final Object factoryProxy) {
		super();
		this.alias = alias;
		this.factoryObject = factoryObject;
		this.factoryProxy = factoryProxy;
	}

	/**
	 * @param method mètode de factoria, interceptat per el container
	 * @param extraParameters paràmetres amb què es fa la crida; el primer pot
	 *            ser null, i sempre serà sobreescrit per la instància
	 *            <tt>factoryProxy</tt>
	 * @return la instància de bean ja construida i montada
	 */
	protected Object buildInstance(final Method method, final Object[] extraParameters) {
		try {
			return method.invoke(factoryObject, cons(factoryProxy, extraParameters));
		} catch (final Exception e) {
			throw new FrijolesException("building bean instance invoking: " + method.toString(), e);
		}
	}

	public String getAlias() {
		return alias;
	}

	public abstract Object getBean(final Method method, Object[] extraParameters);

	// @SuppressWarnings("unchecked")
	public static <T> T[] cons(final T e, final T[] ts) {
		// final Class<T> componentType = (Class<T>)
		// ts.getClass().getComponentType();
		// final T[] r = (T[]) newInstance(componentType, ts.length + 1);
		// arraycopy(ts, 0, r, 1, ts.length);
		// r[0] = e;
		final T[] r = ts;
		r[0] = e;
		return ts;
	}

}