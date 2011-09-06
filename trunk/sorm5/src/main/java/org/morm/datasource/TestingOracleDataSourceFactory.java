package org.morm.datasource;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

import org.morm.exception.FException;

public class TestingOracleDataSourceFactory {

	public DataSource getDataSource() {
		try {
			final OracleDataSource ds = new OracleDataSource();

			ds.setURL("jdbc:oracle:thin:@172.23.65.15:1528:ACO_003");
			ds.setUser("system");
			ds.setPassword("password");

			return ds;
		} catch (final Exception e) {
			throw new FException(e);
		}
	}

}
