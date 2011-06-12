package org.frijoles3.holder.impl;

import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;

import org.frijoles3.exception.FrijolesException;
import org.frijoles3.holder.AbstractHolder;

public class Session extends AbstractHolder {

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
			throw new FrijolesException("one of the factory method arguments must be an "
					+ HttpSession.class.getSimpleName() + " instance, in factory method: "
					+ method.toString());
		}

		// l'objecte null serà prototype
		if (session.getAttribute(method.getName()) == null) {
			final Object bean = buildInstance(method, extraParameters);
			session.setAttribute(method.getName(), bean);
			return bean;
		}

		return session.getAttribute(method.getName());

	}

	@Override
	public String toString() {
		return getAlias() + "={request}";
	}

}
