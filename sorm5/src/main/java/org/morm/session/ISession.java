package org.morm.session;

import java.sql.Connection;

public interface ISession {

	Connection openTransaction();

	Connection getConnection();

	void commit();

	void rollback();

	void close();

	boolean isTransactionActive();

}
