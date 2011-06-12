package org.frijoles3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.frijoles3.exception.FrijolesException;

import java.util.Arrays;

public class ProxyUtils {

	public static Object buildProxy(final Object bean, final InvocationHandler o) {

		final Class<? extends Object> beanClass = bean.getClass();

		final Class<?>[] allInterfaces;
		if (bean instanceof Deproxable) {
			allInterfaces = beanClass.getInterfaces();
		} else {
			allInterfaces = cons(Deproxable.class, beanClass.getInterfaces());// TODO
		}

		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), allInterfaces, o);
	}

	protected static <T> T[] cons(final T element, final T[] array) {
		final T[] r = Arrays.copyOf(array, array.length + 1);
		r[array.length] = element;
		return r;
	}

	protected static <T> T newInstanceOf(final Class<? extends T> claz) {
		T r;
		try {
			r = claz.newInstance();
		} catch (final Exception e) {
			throw new FrijolesException("cannot create " + claz.toString()
					+ ", it is visible, with a public default constructor?", e);
		}
		return r;
	}

	public static boolean isDeproxMethod(final Method method) {
		return method.getName().equals("deprox") && method.getParameterTypes().length == 0;
		// try {
		// return Deproxable.class.getMethod("deprox").equals(method);
		// } catch (final Exception e) {
		// throw new RuntimeException(e);
		// }
	}
}
