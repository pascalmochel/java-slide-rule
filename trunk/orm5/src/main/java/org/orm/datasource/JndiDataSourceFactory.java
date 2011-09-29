package org.orm.datasource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.orm.exception.OrmException;

/**
 * <pre>
 * new JndiDataSourceFactory(&quot;java:/comp/env/jdbc/DBTest&quot;).getDataSource();
 * </pre>
 * <p>
 * <b><tt>$TOMCAT_HOME/conf/server.xml</tt></b>
 * <p>
 * 
 * <pre>
 * &lt;Context ...
 * 	&lt;Resource auth=&quot;Container&quot; driverClassName=&quot;org.hsqldb.jdbcDriver&quot;
 * 	maxActive=&quot;100&quot; maxIdle=&quot;30&quot; maxWait=&quot;10000&quot;
 * 	name=&quot;jdbc/DBTest&quot; username=&quot;sa&quot; password=&quot;&quot; type=&quot;javax.sql.DataSource&quot;
 * 	url=&quot;jdbc:hsqldb:file:/home/mhoms/usuarios&quot; /&gt;
 * </pre>
 * 
 * @author mhoms
 */
public class JndiDataSourceFactory {

	protected final String jndiName;

	public JndiDataSourceFactory(final String jndiName) {
		super();
		this.jndiName = jndiName;
	}

	public DataSource getDataSource() {

		InitialContext cxt;
		try {
			cxt = new InitialContext();
			if (cxt == null) {
				throw new OrmException("no JNDI context available");
			}

			final DataSource ds = (DataSource) cxt.lookup(jndiName);

			if (ds == null) {
				throw new OrmException("Data source not found: " + jndiName);
			}
			return ds;
		} catch (final NamingException exc) {
			throw new OrmException("error acquiring InitialContext, for jndi name " + jndiName, exc);
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + jndiName;
	}

}
