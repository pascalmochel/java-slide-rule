package org.frijoles3.aop;

import java.lang.reflect.Method;

public class MethodCall {

	protected final Object bean;
	protected final Method method;
	protected final Object[] arguments;

	public MethodCall(final Object bean, final Method method, final Object[] arguments) {
		super();
		this.bean = bean;
		this.method = method;
		this.arguments = arguments.clone();
	}

	public Object getBean() {
		return bean;
	}

	public Method getMethod() {
		return method;
	}

	public Object[] getArguments() {
		return arguments.clone();
	}

	public Object invoke() {
		try {
			return method.invoke(bean, arguments);
		} catch (final Exception e) {
			throw new RuntimeException("exception invoking " + method.toGenericString(), e);
		}
	}

}
