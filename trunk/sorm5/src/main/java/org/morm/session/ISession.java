package org.morm.session;

import java.sql.Connection;

public interface ISession {

	/**
	 * @see org.morm.session.ISession#beginTransaction()
	 */
	public abstract Connection beginTransaction();

	/**
	 * @see org.morm.session.ISession#getConnection()
	 */
	public abstract Connection getConnection();

	/**
	 * @see org.morm.session.ISession#commit()
	 */
	public abstract void commit();

	/**
	 * @see org.morm.session.ISession#rollback()
	 */
	public abstract void rollback();

	/**
	 * @see org.morm.session.ISession#isTransactionActive()
	 */
	public abstract boolean isTransactionActive();

}
