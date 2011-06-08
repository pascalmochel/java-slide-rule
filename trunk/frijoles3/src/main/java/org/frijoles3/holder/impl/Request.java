package org.frijoles3.holder.impl;

import java.lang.reflect.Method;

import javax.servlet.ServletRequest;

import org.frijoles3.holder.AbstractHolder;

public class Request extends AbstractHolder {

	// protected Object bean;
	// protected volatile boolean initializated;
	// protected Object beanMutex = new Object();

	public Request(final String alias, final Object factoryObject, final Object factoryProxy) {
		super(alias, factoryObject, factoryProxy);
	}

	@Override
	public Object getBean(final Method method, final Object[] extraParameters) {

		ServletRequest req = null;
		for (int i = 1; i < extraParameters.length; i++) {
			if (extraParameters[i] instanceof ServletRequest) {
				req = (ServletRequest) extraParameters[i];
				break;
			}
		}
		if (req == null) {
			throw new RuntimeException();
		}

		if (req.getAttribute(method.getName()) == null) {
			final Object bean = buildInstance(method, extraParameters);
			req.setAttribute(method.getName(), bean);
			return bean;
		}

		return req.getAttribute(method.getName());

	}

	// @Override
	// public boolean isInitializated() {
	// return initializated;
	// }
	//
	// @Override
	// public String getScopedBeanNature() {
	// return "singleton";
	// }
	//
	@Override
	public String toString() {
		return getAlias() + "={request}";
	}

}
