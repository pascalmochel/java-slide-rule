package org.frijoles3.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.frijoles3.Deproxable;
import org.frijoles3.exception.FrijolesException;

import java.util.Arrays;

public class Intercept implements InvocationHandler, Deproxable {

	protected Object bean;
	protected Interceptor interceptor;

	protected Intercept(final Object bean, final Interceptor interceptor) {
		// non-visible constructor
		super();
		this.bean = bean;
		this.interceptor = interceptor;
	}

	@SuppressWarnings("unchecked")
	public static <T> T with(final T bean, final Interceptor interceptor) {
		// public static Object with(final Object bean, final Interceptor
		// interceptor) {

		final Class<? extends Object> beanClass = bean.getClass();
		// final Class<?>[] allInterfaces = beanClass.getInterfaces();
		final Class<?>[] allInterfaces = cons(Deproxable.class, beanClass.getInterfaces());// TODO

		return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), allInterfaces,
				new Intercept(bean, interceptor));
	}

	public Object invoke(final Object proxy, final Method method, final Object[] arguments) {

		if (method.getName().equals("deprox") && method.getParameterTypes().length == 0) {
			return deprox();
		}

		try {
			return interceptor.intercept(new MethodCall(bean, method, arguments));
		} catch (final Exception e) {
			throw new FrijolesException("error during execution of intercepted bean method: "
					+ method.getName(), e);
		}
	}

	public Object deprox() {
		return bean;
	}

	private static <T> T[] cons(final T element, final T[] array) {
		final T[] r = Arrays.copyOf(array, array.length + 1);
		r[array.length] = element;
		return r;
	}
}
