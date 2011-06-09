package org.frijoles3;

import java.lang.reflect.Constructor;

import org.frijoles3.exception.FrijolesException;
import org.frijoles3.holder.AbstractHolder;

public class ReflectUtils {

	protected ReflectUtils() {
	}

	public static <T> T newInstanceOf(final Class<? extends T> claz) {
		T r;
		try {
			r = claz.newInstance();
		} catch (final Exception e) {
			throw new FrijolesException("cannot create " + claz.toString()
					+ ", it is visible, with a public default constructor?", e);
		}
		return r;
	}

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
