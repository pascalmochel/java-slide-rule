package org.frijoles4.scope.impl;

import javax.servlet.ServletRequest;

import org.frijoles4.exception.FrijolesException;
import org.frijoles4.obtainer.IBeanObtainer;
import org.frijoles4.scope.ScopedBean;

public class Request extends ScopedBean {

	public Request(final String alias, final IBeanObtainer beanObtainer) {
		super(alias, beanObtainer);
	}

	@Override
	public Object getBean(final Object... args) {

		ServletRequest req = null;
		for (int i = 1; i < args.length; i++) {
			if (args[i] instanceof ServletRequest) {
				req = (ServletRequest) args[i];
				break;
			}
		}
		if (req == null) {
			throw new FrijolesException(
					"one of arguments invoking a request/session scoped context method must be of type "
							+ ServletRequest.class);
		}

		// l'objecte null serà prototype
		if (req.getAttribute(alias) == null) {
			final Object bean = beanObtainer.obtain(args);
			req.setAttribute(alias, bean);
			return bean;
		}

		return req.getAttribute(alias);
	}

	@Override
	public String toString() {
		return alias + "={request}";
	}

}
