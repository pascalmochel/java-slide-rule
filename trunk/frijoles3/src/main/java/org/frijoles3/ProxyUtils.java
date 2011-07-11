package org.frijoles3;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.frijoles3.exception.FrijolesException;

public class ProxyUtils {

	public static Object buildInterceptingProxy(final Object bean, final InvocationHandler o) {

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

	public static Object buildProxy(final Object bean, final InvocationHandler o) {

		final Class<? extends Object> beanClass = bean.getClass();

		final Class<?>[] interfaces = beanClass.getInterfaces();
		if (interfaces.length == 0) {
			throw new FrijolesException("object factory must implements almost one interface: "
					+ beanClass.getSimpleName());
		}

		Class<?>[] allInterfaces = interfaces;
		if (!(bean instanceof Deproxable)) {
			allInterfaces = cons(Deproxable.class, allInterfaces);
		}
		if (!(bean instanceof AliasGetter)) {
			allInterfaces = cons(AliasGetter.class, allInterfaces);
		}

		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), allInterfaces, o);
	}

	protected static Class<?>[] cons(final Class<?> element, final Class<?>[] array) {
		final Class<?>[] r = (Class<?>[]) Array.newInstance(Class.class, array.length + 1);
		System.arraycopy(array, 0, r, 0, array.length);
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

}
