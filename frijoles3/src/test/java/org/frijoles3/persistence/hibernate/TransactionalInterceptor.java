package org.frijoles3.persistence.hibernate;

import java.lang.reflect.Method;

import org.frijoles3.aop.Interceptor;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TransactionalInterceptor implements Interceptor {

	public Object intercept(final Object targetBean, final Method method, final Object[] arguments)
			throws Exception {

		if (method.getName().startsWith("find") || method.getName().startsWith("load")) {
			return method.invoke(targetBean, arguments);
		} else {
			return transactionalInvoke(targetBean, method, arguments);
		}
	}

	protected Object transactionalInvoke(final Object targetBean, final Method method,
			final Object[] arguments) {
		final Session session = HibernateSessionFactory.getSession();
		final Transaction tx = session.beginTransaction();
		System.out.println("open");
		try {
			final Object r = method.invoke(targetBean, arguments);
			tx.commit();
			System.out.println("commit");
			return r;
		} catch (final Exception e) {
			tx.rollback();
			System.out.println("rollback");
			throw new RuntimeException(e);
		}
	}

}
