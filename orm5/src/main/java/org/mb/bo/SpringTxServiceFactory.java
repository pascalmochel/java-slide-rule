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
package org.mb.bo;

import org.mb.exception.OrmException;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * utility class for a quick configuration of transactional services in Spring
 * context.
 * <p>
 * <ul>
 * <li>Property {@link #txInterceptorBeanName} is optional, using by default the
 * bean named as {@link #DEFAULT_TX_INTERCEPTOR_BEAN_NAME}.
 * <li>Property {@link SpringTxServiceFactory#targetService} is the service
 * object to intercept and make it transactional, and a set is required.
 * </ul>
 * <p>
 * By implementing the {@link ProxyFactoryBean} class, this object becomes an
 * AOP interceptor, working for made service objects transactional.
 * <p>
 * This object extends and specializes {@link ProxyFactoryBean} for the purposes
 * of making simple the Spring configuration.
 * <p>
 * You can use the {@link org.sorm.spring.tx.SpringTxInterceptor} transaction
 * demarcation mechanism together with the
 * {@link org.sorm.spring.tx.SpringTxServiceFactory} for a less verbose
 * configuration file, since this object is sintactic sugar:
 * 
 * <pre>
 * &lt;bean id=&quot;txInterceptor&quot; class=&quot;org.sorm.tx.TxInterceptor&quot; /&gt;
 * &lt;bean id=&quot;exampleService&quot; class=&quot;org.sorm.config.TxServiceConfig&quot;&gt;
 *     &lt;property name=&quot;targetService&quot;&gt;
 *         &lt;bean class=&quot;org.sorm.example.bo.ExampleServiceImpl&quot;&gt;
 *             &lt;property name=&quot;personDao&quot; ref=&quot;personDao&quot; /&gt;
 *             &lt;property name=&quot;cityDao&quot; ref=&quot;cityDao&quot; /&gt;
 *         &lt;/bean&gt;
 *     &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * <p>
 * By default, {@link SpringTxServiceFactory} uses the interceptor bean named "
 * <tt>txInterceptor</tt>" in the Spring configuration file, but a different
 * name can be specified via "<tt>txInterceptorBeanName</tt>" property:
 * 
 * <pre>
 * &lt;bean id=&quot;myTxInterceptor&quot; class=&quot;org.sorm.tx.TxInterceptor&quot; /&gt;
 * &lt;bean id=&quot;exampleService&quot; class=&quot;org.sorm.config.TxServiceConfig&quot;&gt;
 *     &lt;property name=&quot;txInterceptorBeanName&quot; value=&quot;myTxInterceptor&quot; /&gt;
 *     &lt;property name=&quot;targetService&quot;&gt;
 *         &lt;bean class=&quot;org.sorm.example.bo.ExampleServiceImpl&quot;&gt;
 *             &lt;property name=&quot;personDao&quot; ref=&quot;personDao&quot; /&gt;
 *             &lt;property name=&quot;cityDao&quot; ref=&quot;cityDao&quot; /&gt;
 *         &lt;/bean&gt;
 *     &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * @see org.sorm.spring.tx.SpringTxInterceptor
 * @author mhoms
 */
public class SpringTxServiceFactory extends ProxyFactoryBean implements InitializingBean {

	private static final long serialVersionUID = -8292084176259111576L;

	public static final String DEFAULT_TX_INTERCEPTOR_BEAN_NAME = "txInterceptor";

	protected String txInterceptorBeanName = DEFAULT_TX_INTERCEPTOR_BEAN_NAME;
	protected Object targetService;

	public void setTxInterceptorBeanName(final String txInterceptorBeanName) {
		this.txInterceptorBeanName = txInterceptorBeanName;
	}

	public void setTargetService(final Object targetService) {
		this.targetService = targetService;
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		if (targetService == null) {
			throw new OrmException(this.getClass().getSimpleName()
					+ " needs the 'targetService' property to be already injected");
		}

		super.setInterceptorNames(new String[] { txInterceptorBeanName });
		try {
			super.setProxyInterfaces(targetService.getClass().getInterfaces());
		} catch (final ClassNotFoundException e) {
			throw new OrmException("in " + getClass().getSimpleName() + ", interface not found", e);
		}
		super.setTarget(targetService);
	}

}
