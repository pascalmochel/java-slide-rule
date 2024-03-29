package org.mb.session;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import javax.sql.DataSource;

import org.mb.exception.OrmException;
import org.mb.record.Entity;
import org.mb.session.identity.IdentityCache;
import org.mb.session.identity.StoredSet;

import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NestableSession implements ISession {

	private final static Logger LOG = Logger.getLogger(NestableSession.class.getName());

	protected final DataSource dataSource;
	protected final Integer transactionIsolation;
	protected Connection connection;
	protected final Stack<Savepoint> txStack;

	protected final IdentityCache<Entity> idCache = new IdentityCache<Entity>();
	protected final StoredSet storedSet = new StoredSet();

	/**
	 * C'tor
	 * 
	 * @param dataSource the {@link DataSource} reference
	 */
	public NestableSession(final DataSource dataSource, final Integer transactionIsolation) {
		super();
		this.dataSource = dataSource;

		if (transactionIsolation == null) {
			this.transactionIsolation = Connection.TRANSACTION_READ_COMMITTED;
		} else {
			this.transactionIsolation = transactionIsolation;
		}
		txStack = new Stack<Savepoint>();
	}

	public Connection open() {

		if (isTransactionActive()) {
			final String savePointName = String.valueOf(txStack.size());
			try {
				final Savepoint savePoint = connection.setSavepoint(savePointName);
				txStack.push(savePoint);
				if (LOG.isLoggable(Level.FINEST)) {
					LOG.finest("tx begin: " + savePointName);
				}
			} catch (final SQLException exc) {
				throw new OrmException(
						"throwed exception attempting to establish a transaction savepoint: savePointName="
								+ savePointName, exc);
			}
		} else {
			try {
				connection = dataSource.getConnection();
				connection.setAutoCommit(false);
				connection.setTransactionIsolation(transactionIsolation);
				if (LOG.isLoggable(Level.FINEST)) {
					LOG.finest("tx begin");
				}
			} catch (final SQLException exc) {
				throw new OrmException("throwed exception attempting to obtain a fresh connection", exc);
			}
		}
		return connection;
	}

	public Connection getConnection() {
		if (connection == null) {
			throw new OrmException("business operation are ocurred outside of a transaction bounds");
		}
		return connection;
	}

	public void commit() {
		if (txStack.isEmpty()) {
			try {
				connection.commit();
				if (LOG.isLoggable(Level.FINEST)) {
					LOG.finest("tx commit");
				}
				close();
			} catch (final SQLException exc) {
				throw new OrmException("throwed exception during transaction commit", exc);
			}
		} else {
			String savePointName = null;
			try {
				final Savepoint savePoint = txStack.pop();
				savePointName = savePoint.getSavepointName();
				connection.releaseSavepoint(savePoint);
				if (LOG.isLoggable(Level.FINEST)) {
					LOG.finest("tx commit: " + savePointName);
				}
			} catch (final SQLException exc) {
				throw new OrmException(
						"throwed exception during transaction releaseSavepoint; savePointName:"
								+ savePointName, exc);
			}
		}
	}

	public void rollback() {
		if (connection == null) {
			throw new OrmException("null connection; transaction not opened");
		}
		if (txStack.isEmpty()) {
			try {
				connection.rollback();
				if (LOG.isLoggable(Level.FINEST)) {
					LOG.finest("tx rollback");
				}
				close();
			} catch (final SQLException exc) {
				throw new OrmException("throwed exception during transaction rollback", exc);
			}
		} else {
			String savePointName = null;
			try {
				final Savepoint savePoint = txStack.pop();
				savePointName = savePoint.getSavepointName();
				connection.rollback(savePoint);
				if (LOG.isLoggable(Level.FINEST)) {
					LOG.finest("tx rollback: " + savePointName);
				}
			} catch (final SQLException exc) {
				throw new OrmException("throwed exception during transaction rollback. savePointName="
						+ savePointName, exc);
			}
		}
	}

	public void closeReadOnly() {
		if (connection == null) {
			throw new OrmException("null connection; transaction not opened");
		}
		if (txStack.isEmpty()) {
			if (LOG.isLoggable(Level.FINEST)) {
				LOG.finest("tx closeReadOnly");
			}
			close();
		} else {
			final String savePointName = null;
			txStack.pop();
			if (LOG.isLoggable(Level.FINEST)) {
				LOG.finest("tx closeReadOnly: " + savePointName);
			}
		}
	}

	protected void close() {
		try {
			if (!connection.isClosed()) {
				connection.close();
			}
		} catch (final SQLException exc) {
			throw new OrmException("throwed exception closing connection", exc);
		}
		if (LOG.isLoggable(Level.FINEST)) {
			LOG.finest("tx closed");
		}
	}

	public boolean isTransactionActive() {
		try {
			return connection != null && !connection.isClosed();
		} catch (final SQLException e) {
			throw new OrmException("throwed exception reading status from a connection", e);
		}
	}

	public StoredSet getStoredSet() {
		return storedSet;
	}

	public IdentityCache<Entity> getIdCache() {
		return idCache;
	}

}
