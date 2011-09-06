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

import org.morm.exception.SormException;

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

	/**
	 * @see org.sorm.datasource.IDataSourceFactory#getDataSource()
	 */
	public DataSource getDataSource() {

		InitialContext cxt;
		try {
			cxt = new InitialContext();
			if (cxt == null) {
				throw new SormException("no JNDI context available");
			}

			final DataSource ds = (DataSource) cxt.lookup(jndiName);

			if (ds == null) {
				throw new SormException("Data source not found: " + jndiName);
			}
			return ds;
		} catch (final NamingException exc) {
			throw new SormException("error acquiring InitialContext, for jndi name " + jndiName, exc);
		}
	}

}
