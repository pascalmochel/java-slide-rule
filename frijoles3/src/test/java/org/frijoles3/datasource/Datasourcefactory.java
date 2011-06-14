package org.frijoles3.datasource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hsqldb.jdbc.jdbcDataSource;

public class Datasourcefactory implements IDatasourcefactory {

	/**
	 * p.ex.
	 * <tt>"jdbc:hsqldb:file:/home/mhoms/hsql-db;shutdown=true", "sa", null</tt>
	 * ,
	 */
	public DataSource getHsqldbDataSource(final String url, final String user, final String pass) {

		final jdbcDataSource ds = new jdbcDataSource();
		ds.setDatabase(url);
		ds.setUser(user);
		ds.setPassword(pass);
		return ds;
	}

	public DataSource getJndiDataSource(final String jndiName) {

		InitialContext cxt;
		try {
			cxt = new InitialContext();
			if (cxt == null) {
				throw new RuntimeException("no JNDI context available");
			}

			final DataSource ds = (DataSource) cxt.lookup(jndiName);

			if (ds == null) {
				throw new RuntimeException("Data source not found: " + jndiName);
			}
			return ds;
		} catch (final NamingException exc) {
			throw new RuntimeException("error acquiring InitialContext, for jndi name " + jndiName, exc);
		}
	}

}
