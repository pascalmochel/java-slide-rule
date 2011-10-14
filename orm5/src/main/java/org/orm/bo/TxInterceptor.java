/*
 * Copyright (C) 2009, 2010 M. Lechouga
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.orm.bo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.orm.exception.OrmException;
import org.orm.session.SessionFactory;

import java.util.logging.Logger;

/**
 * simple interceptor (dynamic proxy) that mades service objects transactional.
 * <p>
 * Spring version of this one is {@link SpringTxInterceptor}.
 * <p>
 * It simply executes SessionFactory.getCurrentSession().beginTransaction()
 * before each call, and after it, invokes
 * SessionFactory.getCurrentSession().commit()/.rollback() depending if an
 * exception is catched.
 * 
 * @author mhoms
 */
public class TxInterceptor implements InvocationHandler {

	private final static Logger LOG = Logger.getLogger(TxInterceptor.class.getName());

	protected Object service;

	protected TxInterceptor(final Object service) {
		// non-visible constructor
		super();
		this.service = service;
	}

	/**
	 * @param service the service wich have to intercept, and made transactional
	 * @return the dynamic proxy that intercepts all business calls
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createInstance(final T service) {

		return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), service.getClass()
				.getInterfaces(), new TxInterceptor(service));
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(final Object proxy, final Method method, final Object[] args) {

		SessionFactory.getSession().open();
		try {
			LOG.finer("invoking service method: " + method.getName());

			final Object result = method.invoke(service, args);
			SessionFactory.getSession().commit();
			return result;

		} catch (final Exception e) {
			SessionFactory.getSession().rollback();
			throw new OrmException("error during execution of service method: " + method.getName(), e);
		}
	}

}
