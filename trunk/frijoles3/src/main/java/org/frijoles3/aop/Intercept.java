package org.frijoles3.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.frijoles3.Deproxable;
import org.frijoles3.ProxyUtils;
import org.frijoles3.exception.FrijolesException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Intercept implements InvocationHandler, Deproxable {

	protected Object bean;
	protected Interceptor interceptor;
	protected final Map<Method, Boolean> deproxMap;

	protected Intercept(final Object bean, final Interceptor interceptor) {
		// non-visible constructor
		super();
		this.bean = bean;
		this.interceptor = interceptor;
		this.deproxMap = Collections.synchronizedMap(new HashMap<Method, Boolean>());
	}

	@SuppressWarnings("unchecked")
	public static <T> T with(final T bean, final Interceptor interceptor) {

		return (T) ProxyUtils.buildProxy(bean, new Intercept(bean, interceptor));
	}

	public Object invoke(final Object proxy, final Method method, final Object[] arguments) {

		Boolean isDeprox = deproxMap.get(method);
		if (isDeprox == null) {
			isDeprox = ProxyUtils.isDeproxMethod(method);
			deproxMap.put(method, isDeprox);
		}
		if (isDeprox) {
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

}
