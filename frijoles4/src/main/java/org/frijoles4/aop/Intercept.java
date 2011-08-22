package org.frijoles4.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.frijoles4.exception.FrijolesException;

// TODO interceptar si methodName.matches(regexp)
public class Intercept implements InvocationHandler, Deproxable {

	protected Object bean;
	protected Interceptor interceptor;
	protected final Method deproxMethod;

	protected Intercept(final Object bean, final Interceptor interceptor) {
		// non-visible constructor
		super();
		this.bean = bean;
		this.interceptor = interceptor;
		try {
			this.deproxMethod = Deproxable.class.getMethod(Deproxable.DEPROX_METHOD_NAME);
		} catch (final Exception e) {
			throw new FrijolesException("internal failure; " + Deproxable.class.getName() + " has changed?",
					e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T with(final T bean, final Interceptor interceptor) {
		return (T) ProxyUtils.buildDeproxableProxy(bean, new Intercept(bean, interceptor));
	}

	public Object invoke(final Object proxy, final Method method, final Object[] arguments) {

		if (this.deproxMethod.equals(method)) {
			return deprox();
		}

		try {
			return interceptor.intercept(bean, method, arguments);
		} catch (final Exception e) {
			throw new FrijolesException("error during execution of intercepted bean method: "
					+ method.getName(), e);
		}
	}

	public Object deprox() {
		return bean;
	}

}
