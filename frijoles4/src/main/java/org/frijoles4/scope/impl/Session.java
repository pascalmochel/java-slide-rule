package org.frijoles4.scope.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.frijoles4.exception.FrijolesException;
import org.frijoles4.obtainer.IBeanObtainer;
import org.frijoles4.scope.ScopedBean;

public class Session extends ScopedBean {

	public Session(final String alias, final IBeanObtainer beanObtainer) {
		super(alias, beanObtainer);
	}

	@Override
	public Object getBean(final Object... args) {

		HttpSession session = null;
		for (int i = 1; i < args.length; i++) {
			if (args[i] instanceof HttpSession) {
				session = (HttpSession) args[i];
				break;
			}
			if (args[i] instanceof HttpServletRequest) {
				session = ((HttpServletRequest) args[i]).getSession();
				break;
			}
		}
		if (session == null) {
			throw new FrijolesException(
					"one of arguments invoking a request/session scoped context method must be of type "
							+ HttpServletRequest.class + "/" + HttpSession.class);
		}

		// l'objecte null serà prototype
		if (session.getAttribute(alias) == null) {
			final Object bean = beanObtainer.obtain(args);
			session.setAttribute(alias, bean);
			return bean;
		}

		return session.getAttribute(alias);
	}

	@Override
	public String toString() {
		return alias + "={session}";
	}

}
