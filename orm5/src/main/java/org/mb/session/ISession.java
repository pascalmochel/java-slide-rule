package org.mb.session;

import java.sql.Connection;

import org.mb.record.Entity;
import org.mb.session.identity.IdentityCache;
import org.mb.session.identity.StoredSet;

public interface ISession {

	Connection open();

	Connection getConnection();

	void commit();

	void rollback();

	void closeReadOnly();

	boolean isTransactionActive();

	StoredSet getStoredSet();

	IdentityCache<Entity> getIdCache();
}
