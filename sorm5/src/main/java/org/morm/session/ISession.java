package org.morm.session;

import java.sql.Connection;

public interface ISession {

	Connection beginTransaction();

	Connection getConnection();

	void commit();

	void rollback();

	boolean isTransactionActive();

}
