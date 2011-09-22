//package org.morm.session;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.logging.Logger;
//
//import javax.sql.DataSource;
//
//import org.morm.exception.SormException;
//import org.morm.record.Entity;
//import org.morm.session.identity.IdentityMap;
//import org.morm.session.identity.StoredSet;
//
//public class SimpleSession implements ISession {
//
//	private final static Logger LOG = Logger.getLogger(SimpleSession.class.getName());
//
//	protected final DataSource dataSource;
//	protected final Integer transactionIsolation;
//	protected Connection connection;
//
//	protected final IdentityMap<Entity> identityMap = new IdentityMap<Entity>();
//	protected final StoredSet storedSet = new StoredSet();
//
//	/**
//	 * C'tor
//	 * 
//	 * @param dataSource the {@link DataSource} reference
//	 */
//	public SimpleSession(final DataSource dataSource, final Integer transactionIsolation) {
//		super();
//		this.dataSource = dataSource;
//
//		if (transactionIsolation == null) {
//			this.transactionIsolation = Connection.TRANSACTION_READ_COMMITTED;
//		} else {
//			this.transactionIsolation = transactionIsolation;
//		}
//	}
//
//	public Connection open() {
//
//		if (!isTransactionActive()) {
//			try {
//				identityMap.clear();
//				connection = dataSource.getConnection();
//				connection.setAutoCommit(false);
//				connection.setTransactionIsolation(transactionIsolation);
//				LOG.finest("tx begin");
//			} catch (final SQLException exc) {
//				throw new SormException("throwed exception attempting to obtain a fresh connection", exc);
//			}
//		}
//		return connection;
//	}
//
//	public Connection getConnection() {
//		if (connection == null) {
//			throw new SormException("business operation are ocurred outside of a transaction bounds");
//		}
//		return connection;
//	}
//
//	public void commit() {
//
//		try {
//			connection.commit();
//			LOG.finest("tx commit");
//			close();
//		} catch (final SQLException exc) {
//			throw new SormException("throwed exception during transaction commit", exc);
//		}
//	}
//
//	public void rollback() {
//		if (connection == null) {
//			throw new SormException("null connection; transaction not opened");
//		}
//		try {
//			connection.rollback();
//			LOG.finest("tx rollback");
//			close();
//		} catch (final SQLException exc) {
//			throw new SormException("throwed exception during transaction rollback", exc);
//		}
//	}
//
//	protected void close() {
//		try {
//			if (!connection.isClosed()) {
//				connection.close();
//			}
//		} catch (final SQLException exc) {
//			throw new SormException("throwed exception closing connection", exc);
//		}
//		LOG.finest("tx closed");
//	}
//
//	public boolean isTransactionActive() {
//		try {
//			return connection != null && !connection.isClosed();
//		} catch (final SQLException e) {
//			throw new SormException("throwed exception reading status from a connection", e);
//		}
//	}
//
//	public IdentityMap<Entity> getIdentityMap() {
//		return identityMap;
//	}
//
//	public StoredSet getStoredSet() {
//		return storedSet;
//	}
//
// }
