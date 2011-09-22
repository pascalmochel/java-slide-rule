package org.morm.session;

import java.sql.Connection;

import org.morm.record.Entity;
import org.morm.session.identity.IdentityMap;
import org.morm.session.identity.StoredSet;

public interface ISession {

	Connection open();

	Connection getConnection();

	void commit();

	void rollback();

	void closeAsReadOnly();

	boolean isTransactionActive();

	IdentityMap<Entity> getIdentityMap();

	StoredSet getStoredSet();
}
