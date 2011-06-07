package org.frijoles3;

import java.lang.reflect.Constructor;

import org.frijoles3.holder.Holder;

public class ReflectUtils {

	private ReflectUtils() {
	}

	public static <T> T newInstanceOf(final Class<? extends T> claz) {
		T r;
		try {
			r = claz.newInstance();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		return r;
	}

	public static Holder constructHolder(final Constructor<? extends Holder> constructor, final String alias,
			final Object factoryObject, final Object proxy) {
		final Holder holder;
		try {
			holder = constructor.newInstance(alias, factoryObject, proxy);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		return holder;
	}

	public static Constructor<? extends Holder> holderConstructor(final Class<? extends Holder> holderClass) {
		try {
			return holderClass.getConstructor(String.class, Object.class, Object.class);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
