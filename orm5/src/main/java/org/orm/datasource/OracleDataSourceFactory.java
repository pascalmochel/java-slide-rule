//package org.orm.datasource;
//
//import javax.sql.DataSource;
//
//import oracle.jdbc.pool.OracleDataSource;
//
//import org.orm.exception.OrmException;
//
//public class OracleDataSourceFactory {
//
//	protected final String url;
//	protected final String user;
//	protected final String pass;
//
//	public OracleDataSourceFactory() {
//		this("jdbc:oracle:thin:@172.23.65.15:1528:ACO_003", "system", "password");
//	}
//
//	public OracleDataSourceFactory(final String url, final String user, final String pass) {
//		super();
//		this.url = url;
//		this.user = user;
//		this.pass = pass;
//	}
//
//	public DataSource getDataSource() {
//		try {
//			final OracleDataSource ds = new OracleDataSource();
//
//			ds.setURL(url);
//			ds.setUser(user);
//			ds.setPassword(pass);
//
//			return ds;
//		} catch (final Exception e) {
//			throw new OrmException("datasource error", e);
//		}
//	}
//
//	public String getUrl() {
//		return url;
//	}
//
//	public String getUser() {
//		return user;
//	}
//
//	public String getPass() {
//		return pass;
//	}
//
//	@Override
//	public String toString() {
//		return new StringBuilder()
//		/**/.append(getClass().getSimpleName())
//		/**/.append(" ")
//		/**/.append(", url=")
//		/**/.append(url)
//		/**/.append(", user=")
//		/**/.append(user)
//		/**/.append(", pass=***]")
//		/**/.toString();
//	}
//
// }
