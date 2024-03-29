package org.frijoles3.holder.impl;

import java.lang.reflect.Method;

import javax.servlet.ServletRequest;

import org.frijoles3.exception.FrijolesException;
import org.frijoles3.holder.AbstractHolder;

public class Request extends AbstractHolder {

	public Request(final String alias, final Object factoryObject) {
		super(alias, factoryObject);
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
			throw new FrijolesException("one of the factory method arguments must be an "
					+ ServletRequest.class.getSimpleName() + " instance, in factory method: "
					+ method.toString());
		}

		// l'objecte null serà prototype
		if (req.getAttribute(method.getName()) == null) {
			final Object bean = buildInstance(method, extraParameters);
			req.setAttribute(method.getName(), bean);
			return bean;
		}

		return req.getAttribute(method.getName());

	}

	@Override
	public String toString() {
		return getAlias() + "={request}";
	}

}
