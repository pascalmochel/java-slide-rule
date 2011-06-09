package org.frijoles3.holder.impl;

import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;

import org.frijoles3.holder.AbstractHolder;

public class Session extends AbstractHolder {

	// protected Object bean;
	// protected volatile boolean initializated;
	// protected Object beanMutex = new Object();

	public Session(final String alias, final Object factoryObject) {
		super(alias, factoryObject);
	}

	@Override
	public Object getBean(final Method method, final Object[] extraParameters) {

		HttpSession session = null;
		for (int i = 1; i < extraParameters.length; i++) {
			if (extraParameters[i] instanceof HttpSession) {
				session = (HttpSession) extraParameters[i];
				break;
			}
		}
		if (session == null) {
			throw new RuntimeException("one of the factory method arguments must be an "
					+ HttpSession.class.getSimpleName() + " instance, in factory method: "
					+ method.toString());
		}

		if (session.getAttribute(method.getName()) == null) {
			final Object bean = buildInstance(method, extraParameters);
			session.setAttribute(method.getName(), bean);
			return bean;
		}

		return session.getAttribute(method.getName());

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
