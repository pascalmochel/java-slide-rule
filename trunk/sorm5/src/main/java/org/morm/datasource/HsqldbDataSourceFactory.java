package org.morm.datasource;

import javax.sql.DataSource;

import org.hsqldb.jdbc.jdbcDataSource;

public class HsqldbDataSourceFactory {

	protected final String url;
	protected final String user;
	protected final String pass;

	public HsqldbDataSourceFactory() {
		this("jdbc:hsqldb:file:/home/mhoms/hsql8", "sa", null);
	}

	public HsqldbDataSourceFactory(final String url, final String user, final String pass) {
		super();
		this.url = url;
		this.user = user;
		this.pass = pass;
	}

	public DataSource getDataSource() {

		final jdbcDataSource ds = new jdbcDataSource();

		ds.setDatabase(url); // ;shutdown=true
		ds.setUser(user);
		ds.setPassword(pass);

		return ds;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("HsqldbDataSourceFactory [pass=");
		builder.append(pass);
		builder.append(", url=");
		builder.append(url);
		builder.append(", user=");
		builder.append(user);
		builder.append("]");
		return builder.toString();
	}

}