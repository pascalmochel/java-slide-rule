package org.mb.session;

import javax.sql.DataSource;

import org.mb.exception.OrmException;

import java.util.logging.Logger;

public class SessionFactory {

	protected final static Logger LOG = Logger.getLogger(SessionFactory.class.getName());

	/**
	 * the session is stored in a thread-variable; every thread will have his
	 * own {@link ISession} instance.
	 */
	protected static ThreadLocal<ISession> threadSession = new ThreadLocal<ISession>();

	/**
	 * a DataSource reference, shared by all threads
	 */
	// TODO això no hauria de ser static
	protected static DataSource dataSourceReference;
	protected static Integer transactionIsolation;

	/**
	 * configures the factory with the supplied {@link DataSource}.
	 * 
	 * @param dataSource
	 */
	// TODO i com fer per a varis dataSources?
	public static void configDataSource(final DataSource dataSource) {
		synchronized (dataSource) {
			SessionFactory.dataSourceReference = dataSource;
			LOG.info("SessionFactory properly configured with DataSource: " + dataSource.getClass().getName()
					+ ":" + dataSource.toString());
		}
	}

	public void setDataSource(final DataSource dataSource) {
		configDataSource(dataSource);
	}

	public static void setTransactionIsolation(final Integer transactionIsolation) {
		SessionFactory.transactionIsolation = transactionIsolation;
	}

	/**
	 * @return the current {@link ISession} instance, or if it is inactive,
	 *         creates a new one.
	 * @throws DataMapperException if dataSourceReference is not being injected
	 */
	public static ISession getSession() {
		ISession session = threadSession.get();
		if (!isSessionActive()) {
			session = createNewSession();
			threadSession.set(session);
		}
		return session;
	}

	protected static boolean isSessionActive() {
		return threadSession != null && threadSession.get() != null
				&& threadSession.get().isTransactionActive();
	}

	/**
	 * @return creates a new {@link ISession} instance
	 * @throws DataMapperException if dataSourceReference is not being injected
	 */
	protected static ISession createNewSession() {
		if (dataSourceReference == null) {
			throw new OrmException("configuration error: bean " + SessionFactory.class.getName()
					+ " requires 'dataSourceReference' property injection.");
		}
		return new NestableSession(dataSourceReference, transactionIsolation);
	}

}
