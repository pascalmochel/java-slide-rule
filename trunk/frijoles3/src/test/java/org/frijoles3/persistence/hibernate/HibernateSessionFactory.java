package org.frijoles3.persistence.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html }.
 */
public class HibernateSessionFactory {

	/**
	 * Location of hibernate.cfg.xml file. Location should be on the classpath
	 * as Hibernate uses #resourceAsStream style lookup for its configuration
	 * file. The default classpath location of the hibernate config file is in
	 * the default package. Use #setConfigFile() to update the location of the
	 * configuration file for the current session.
	 */
	// private static String CONFIG_FILE_LOCATION =
	// "/es/caifor/gfa/dao/hibernate.cfg.xml";
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static final Configuration configuration = new Configuration();
	private static org.hibernate.SessionFactory sessionFactory;

	// private static String configFile = CONFIG_FILE_LOCATION;

	private HibernateSessionFactory() {
	}

	public static void setSessionFactory(final org.hibernate.SessionFactory sessionFactory) {
		HibernateSessionFactory.sessionFactory = sessionFactory;
	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the
	 * <code>SessionFactory</code> if needed.
	 * 
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session getSession() {
		Session session = threadLocal.get();

		if (session == null || !session.isOpen()) {
			// if (sessionFactory == null) {
			// rebuildSessionFactory();
			// }
			session = (sessionFactory != null) ? sessionFactory.openSession() : null;
			threadLocal.set(session);
		}

		return session;
	}

	// /**
	// * Rebuild hibernate session factory
	// */
	// synchronized public static void rebuildSessionFactory() {
	// // try {
	// configuration.configure(configFile);
	// sessionFactory = configuration.buildSessionFactory();
	// // } catch (final Exception e) {
	// // System.err.println("%%%% Error Creating SessionFactory %%%%");
	// // e.printStackTrace();
	// // throw new RuntimeException(e);
	// // }
	// }

	/**
	 * Close the single hibernate session instance.
	 * 
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		final Session session = threadLocal.get();
		threadLocal.set(null);

		if (session != null) {
			session.close();
		}
	}

	/**
	 * return session factory
	 */
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	// /**
	// * return session factory session factory will be rebuilded in the next
	// call
	// */
	// public static void setConfigFile(final String configFile) {
	// HibernateSessionFactory.configFile = configFile;
	// sessionFactory = null;
	// }

	/**
	 * return hibernate configuration
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}

}