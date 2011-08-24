package org.frijoles4.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Configures and provides access to Hibernate sessions, tied to the current thread of execution. Follows the
 * Thread Local Session pattern, see {@link http://hibernate.org/42.html }.
 */
public class HibernateSessionFactory {

	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static org.hibernate.SessionFactory sessionFactory;

	private HibernateSessionFactory() {
	}

	public static void setSessionFactory(final org.hibernate.SessionFactory sessionFactory) {
		HibernateSessionFactory.sessionFactory = sessionFactory;
	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the <code>SessionFactory</code> if needed.
	 * 
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session getSession() {
		Session session = threadLocal.get();

		if (session == null || !session.isOpen()) {
			session = (sessionFactory != null) ? sessionFactory.openSession() : null;
			threadLocal.set(session);
		}

		return session;
	}

}
