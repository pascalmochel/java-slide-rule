package org.frijoles4;

import java.lang.reflect.Array;

import org.frijoles4.exception.FrijolesException;

public class Utils {

	public static Object[] cons(final Object element, final Object[] array) {
		final Object[] r = (Object[]) Array.newInstance(Object.class, array.length + 1);
		System.arraycopy(array, 0, r, 1, array.length);
		r[0] = element;
		return r;
	}

	public static <T> T newInstanceOf(final Class<? extends T> claz) {
		T r;
		try {
			r = claz.newInstance();
		} catch (final Exception e) {
			throw new FrijolesException("cannot instantiate " + claz.toString(), e);
		}
		return r;
	}

}
