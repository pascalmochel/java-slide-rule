package org.frijoles4.aop;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.frijoles4.exception.FrijolesException;

public class ProxyUtils {

	protected static Class<?>[] cons(final Class<?> element, final Class<?>[] array) {
		final Class<?>[] r = (Class<?>[]) Array.newInstance(Class.class, array.length + 1);
		System.arraycopy(array, 0, r, 0, array.length);
		r[array.length] = element;
		return r;
	}

	public static Object buildDeproxableProxy(final Object bean, final InvocationHandler o) {

		final Class<? extends Object> beanClass = bean.getClass();

		final Class<?>[] interfaces = beanClass.getInterfaces();
		if (interfaces.length == 0) {
			throw new FrijolesException("object factory must implements almost one interface: "
					+ beanClass.getSimpleName());
		}

		Class<?>[] allInterfaces;
		if (bean instanceof Deproxable) {
			allInterfaces = interfaces;
		} else {
			allInterfaces = cons(Deproxable.class, interfaces);
		}

		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), allInterfaces, o);
	}

}
