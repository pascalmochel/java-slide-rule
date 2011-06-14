package org.frijoles3.datasource;

import javax.sql.DataSource;

import org.frijoles3.anno.Scope;

public interface IDatasourcefactory {

	@Scope
	DataSource getHsqldbDataSource(final String url, final String user, final String pass);

	@Scope
	DataSource getJndiDataSource(final String jndiName);

}