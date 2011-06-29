package org.frijoles3.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.frijoles3.Deproxable;
import org.frijoles3.ProxyUtils;
import org.frijoles3.exception.FrijolesException;

import java.util.regex.Pattern;

// TODO interceptar si methodName.matches(regexp)
public class Intercept implements InvocationHandler, Deproxable {

	protected Object bean;
	protected Interceptor interceptor;
	protected final Method deproxMethod;
	protected Pattern methodNamePattern;

	protected Intercept(final Object bean, final Interceptor interceptor) {
		// non-visible constructor
		super();
		this.bean = bean;
		this.interceptor = interceptor;
		try {
			this.deproxMethod = Deproxable.class.getMethod(Deproxable.DEPROX_METHOD_NAME);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		this.methodNamePattern = null;
	}

	protected Intercept(final Object bean, final Interceptor interceptor, final String methodNameregexp) {
		// non-visible constructor
		this(bean, interceptor);
		this.methodNamePattern = Pattern.compile(methodNameregexp);
	}

	@SuppressWarnings("unchecked")
	public static <T> T with(final T bean, final Interceptor interceptor) {
		return (T) ProxyUtils.buildProxy(bean, new Intercept(bean, interceptor));
	}

	@SuppressWarnings("unchecked")
	public static <T> T with(final T bean, final Interceptor interceptor, final String methodNameregexp) {
		return (T) ProxyUtils.buildProxy(bean, new Intercept(bean, interceptor, methodNameregexp));
	}

	public Object invoke(final Object proxy, final Method method, final Object[] arguments) {

		if (this.deproxMethod.equals(method)) {
			return deprox();
		}

		try {
			if (this.methodNamePattern == null || this.methodNamePattern.matcher(method.getName()).matches()) {
				return interceptor.intercept(bean, method, arguments);
			} else {
				return method.invoke(bean, arguments);
			}
		} catch (final Exception e) {
			throw new FrijolesException("error during execution of intercepted bean method: "
					+ method.getName(), e);
		}
	}

	public Object deprox() {
		return bean;
	}

}
