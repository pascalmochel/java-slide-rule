/*
 * Copyright (C) 2009, 2010 M. Lechouga
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.morm.datasource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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
 * 	&lt;Resource auth="Container" driverClassName="org.hsqldb.jdbcDriver"
 * 	maxActive="100" maxIdle="30" maxWait="10000"
 * 	name="jdbc/DBTest" username="sa" password="" type="javax.sql.DataSource"
 * 	url="jdbc:hsqldb:file:/home/mhoms/usuarios" /&gt;
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

	/**
	 * @see org.sorm.datasource.IDataSourceFactory#getDataSource()
	 */
	public DataSource getDataSource() {

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
