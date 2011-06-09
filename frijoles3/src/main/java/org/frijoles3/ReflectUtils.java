package org.frijoles3;

import java.lang.reflect.Method;

import org.frijoles3.exception.FrijolesException;

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

	public static Method methodOf(final Object proxy, final String methodFactoryName) {
		final Method[] ms = proxy.getClass().getMethods();
		Method candidate = null;
		for (final Method m : ms) {
			if (m.getName().equals(methodFactoryName) && m.getReturnType() != void.class) {
				candidate = m;
				break;
			}
		}
		if (candidate == null) {
			throw new FrijolesException("interceptor factory method not found: " + methodFactoryName
					+ " or not have the proper method signature");
		}
		return candidate;
	}

}
