package org.frijoles3.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.frijoles3.exception.FrijolesException;

public class Intercept implements InvocationHandler {

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

		final Class<? extends Object> beanClass = bean.getClass();
		final Class<?>[] allInterfaces = beanClass.getInterfaces();

		return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), allInterfaces,
				new Intercept(bean, interceptor));
	}

	public Object invoke(final Object proxy, final Method method, final Object[] arguments) {

		try {
			return interceptor.intercept(new MethodCall(bean, method, arguments));
		} catch (final Exception e) {
			throw new FrijolesException("error during execution of intercepted bean method: "
					+ method.getName(), e);
		}
	}

}
