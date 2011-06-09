package org.frijoles3.holder;

import java.lang.reflect.Method;

import org.frijoles3.exception.FrijolesException;

public abstract class AbstractHolder {

	protected final String alias;
	protected final Object factoryObject;

	public AbstractHolder(final String alias, final Object factoryObject) {
		super();
		this.alias = alias;
		this.factoryObject = factoryObject;
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
			return method.invoke(factoryObject, extraParameters);
		} catch (final Exception e) {
			throw new FrijolesException("building bean instance invoking: " + method.toString(), e);
		}
	}

	public String getAlias() {
		return alias;
	}

	public abstract Object getBean(final Method method, Object[] extraParameters);

}