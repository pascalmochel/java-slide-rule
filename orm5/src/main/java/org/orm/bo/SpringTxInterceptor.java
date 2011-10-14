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

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.orm.exception.OrmException;
import org.orm.session.SessionFactory;

import java.util.logging.Logger;

/**
 * Spring interceptor that intercepts all service method calls, making service
 * methods transactional.
 * <p>
 * It simply executes SessionFactory.getCurrentSession().beginTransaction()
 * before each call, and after it, invokes
 * SessionFactory.getCurrentSession().commit()/.rollback() depending if an
 * exception is catched.
 * <p>
 * Here a configuration example:
 * 
 * <pre>
 * &lt;bean id=&quot;txInterceptor&quot; class=&quot;org.sorm.tx.TxInterceptor&quot; /&gt;
 *     &lt;bean id=&quot;abstractService&quot; abstract=&quot;true&quot;&gt;
 *         &lt;property name=&quot;interceptorNames&quot;&gt;
 *             &lt;list&gt;&lt;value&gt;txInterceptor&lt;/value&gt;&lt;/list&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 *     &lt;bean id=&quot;exampleService&quot; class=&quot;org.springframework.aop.framework.ProxyFactoryBean&quot;
 *             parent=&quot;abstractService&quot;&gt;
 *         &lt;property name=&quot;proxyInterfaces&quot; value=&quot;org.sorm.example.bo.ExampleService&quot; /&gt;
 *         &lt;property name=&quot;target&quot;&gt;
 *             &lt;bean class=&quot;org.sorm.example.bo.ExampleServiceImpl&quot;&gt;
 *                 &lt;property name=&quot;personDao&quot; ref=&quot;personDao&quot; /&gt;
 *                 &lt;property name=&quot;cityDao&quot; ref=&quot;cityDao&quot; /&gt;
 *             &lt;/bean&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 *</pre>
 * <p>
 * For a less verbose Spring configuration, see
 * {@link org.sorm.spring.tx.SpringTxServiceFactory}.
 * 
 * @see org.sorm.spring.tx.SpringTxServiceFactory
 * @see SessionFactory
 * @author mhoms
 */
public class SpringTxInterceptor implements MethodInterceptor {

	private final static Logger LOG = Logger.getLogger(SpringTxInterceptor.class.getName());

	/**
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(final MethodInvocation invocation) throws Throwable {

		final Method method = invocation.getMethod();

		SessionFactory.getSession().open();
		try {
			LOG.finer("invoking service method: " + method.toString());

			final Object result = method.invoke(invocation.getThis(), invocation.getArguments());
			SessionFactory.getSession().commit();
			return result;

		} catch (final Exception e) {
			SessionFactory.getSession().rollback();
			throw new OrmException("error during execution of service method: " + invocation.toString(), e);
		}
	}

}
