package org.frijoles4.hibernate;

import java.lang.reflect.Method;

import org.frijoles4.aop.Interceptor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.logging.Logger;

public class TransactionalInterceptor implements Interceptor {

	protected static final Logger LOG = Logger.getLogger(TransactionalInterceptor.class.getName());

	public Object intercept(final Object targetBean, final Method method, final Object[] arguments)
			throws Exception {

		// if (method.getName().startsWith("find") ||
		// method.getName().startsWith("load")) {
		if (TransactionalAnnoPresent(targetBean, method)) {
			return transactionalInvoke(targetBean, method, arguments);
		} else {
			return method.invoke(targetBean, arguments);
		}
	}

	protected Object transactionalInvoke(final Object targetBean, final Method method,
			final Object[] arguments) {
		final Session session = HibernateSessionFactory.getSession();
		final Transaction tx = session.beginTransaction();
		LOG.finer("Transaction begins");
		try {
			final Object r = method.invoke(targetBean, arguments);
			tx.commit();
			LOG.finer("Transaction commit");
			return r;
		} catch (final Exception e) {
			tx.rollback();
			LOG.finer("Transaction rollback");
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	protected boolean TransactionalAnnoPresent(final Object targetBean, final Method m) {
		try {

			if (m.getAnnotation(Transactional.class) != null) {
				return true;
			}
			final Method mImpl = targetBean.getClass().getMethod(m.getName(), m.getParameterTypes());
			return mImpl.getAnnotation(Transactional.class) != null;
		} catch (final Exception e) {
			LOG.severe(e.toString());
			return false;
		}
	}
}
