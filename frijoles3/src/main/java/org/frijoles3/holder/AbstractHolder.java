package org.frijoles3.holder;

import java.lang.reflect.Constructor;
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

	public static AbstractHolder buildHolder(final Class<? extends AbstractHolder> holderClass,
			final String alias, final Object factoryObject, final Object proxy) {

		final Constructor<? extends AbstractHolder> constructor;
		try {
			constructor = holderClass.getConstructor(String.class, Object.class);
		} catch (final Exception e) {
			throw new FrijolesException("valid constructor not found for AbstractHolder implementation: "
					+ holderClass, e);
		}

		final AbstractHolder abstractHolder;
		try {
			abstractHolder = constructor.newInstance(alias, factoryObject);
		} catch (final Exception e) {
			throw new FrijolesException("cannot construct " + AbstractHolder.class.getSimpleName()
					+ " implementation: " + constructor.getDeclaringClass().toString()
					+ "; error during constructor invocations: " + constructor.toGenericString(), e);
		}

		return abstractHolder;
	}

}