package org.orm.session;

import java.sql.Connection;

import org.orm.record.Entity;
import org.orm.session.identity.IdentityMap;
import org.orm.session.identity.StoredSet;

public interface ISession {

	Connection open();

	Connection getConnection();

	void commit();

	void rollback();

	void closeReadOnly();

	boolean isTransactionActive();

	IdentityMap<Entity> getIdentityMap();

	StoredSet getStoredSet();
}
