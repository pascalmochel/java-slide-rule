package org.morm.session;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import javax.sql.DataSource;

import org.morm.exception.SormException;

import java.util.Stack;
import java.util.logging.Logger;

public class Session implements ISession {

	private final static Logger LOG = Logger.getLogger(Session.class.getName());

	protected final DataSource dataSource;
	protected final Integer transactionIsolation;
	protected Connection connection;
	protected final Stack<Savepoint> txStack;

	/**
	 * C'tor
	 * 
	 * @param dataSource the {@link DataSource} reference
	 */
	public Session(final DataSource dataSource, final Integer transactionIsolation) {
		super();
		this.dataSource = dataSource;

		if (transactionIsolation == null) {
			this.transactionIsolation = Connection.TRANSACTION_READ_COMMITTED;
		} else {
			this.transactionIsolation = transactionIsolation;
		}
		txStack = new Stack<Savepoint>();
	}

	public Connection beginTransaction() {

		if (isTransactionActive()) {
			final String savePointName = String.valueOf(txStack.size());
			try {
				final Savepoint savePoint = connection.setSavepoint(savePointName);
				txStack.push(savePoint);
				LOG.finest("tx begin: " + savePointName);
			} catch (final SQLException exc) {
				throw new SormException(
						"throwed exception attempting to establish a transaction savepoint: savePointName="
						+ savePointName, exc);
			}
		} else {
			try {
				connection = dataSource.getConnection();
				connection.setAutoCommit(false);
				connection.setTransactionIsolation(transactionIsolation);
				LOG.finest("tx begin");
			} catch (final SQLException exc) {
				throw new SormException("throwed exception attempting to obtain a fresh connection", exc);
			}
		}
		return connection;
	}

	public Connection getConnection() {
		if (connection == null) {
			// throw new
			// RuntimeException("business operation are ocurred outside of a transaction bounds");
			return beginTransaction();
		}
		return connection;
	}

	public void commit() {
		if (txStack.isEmpty()) {
			try {
				connection.commit();
				LOG.finest("tx commit");
				close();
			} catch (final SQLException exc) {
				throw new SormException("throwed exception during transaction commit", exc);
			}
		} else {
			String savePointName = null;
			try {
				final Savepoint savePoint = txStack.pop();
				savePointName = savePoint.getSavepointName();
				connection.releaseSavepoint(savePoint);
				LOG.finest("tx commit: " + savePointName);
			} catch (final SQLException exc) {
				throw new SormException(
						"throwed exception during transaction releaseSavepoint; savePointName:"
						+ savePointName, exc);
			}
		}
	}

	public void rollback() {
		if (connection == null) {
			throw new RuntimeException("null connection; transaction not opened");
		}
		if (txStack.isEmpty()) {
			try {
				connection.rollback();
				LOG.finest("tx rollback");
				close();
			} catch (final SQLException exc) {
				throw new SormException("throwed exception during transaction rollback", exc);
			}
		} else {
			String savePointName = null;
			try {
				final Savepoint savePoint = txStack.pop();
				savePointName = savePoint.getSavepointName();
				connection.rollback(savePoint);
				LOG.finest("tx rollback: " + savePointName);
			} catch (final SQLException exc) {
				throw new SormException("throwed exception during transaction rollback. savePointName="
						+ savePointName, exc);
			}
		}
	}

	protected void close() {
		try {
			connection.close();
		} catch (final SQLException exc) {
			throw new SormException("throwed exception closing connection", exc);
		}
		LOG.finest("tx closed");
	}

	public boolean isTransactionActive() {
		try {
			return connection != null && !connection.isClosed();
		} catch (final SQLException e) {
			throw new SormException("throwed exception reading status from a connection", e);
		}
	}

}
