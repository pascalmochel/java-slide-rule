package org.morm.session;

import java.sql.Connection;

public interface ISession {

	/**
	 * @see org.morm.session.ISession#beginTransaction()
	 */
	Connection beginTransaction();

	/**
	 * @see org.morm.session.ISession#getConnection()
	 */
	Connection getConnection();

	/**
	 * @see org.morm.session.ISession#commit()
	 */
	void commit();

	/**
	 * @see org.morm.session.ISession#rollback()
	 */
	void rollback();

	/**
	 * @see org.morm.session.ISession#isTransactionActive()
	 */
	boolean isTransactionActive();

}
