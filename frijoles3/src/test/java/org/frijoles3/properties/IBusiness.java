package org.frijoles3.properties;

import javax.sql.DataSource;

public interface IBusiness {

	static final IBusiness X = null;

	DataSource getDataSource(IBusiness self);

	// SessionFactory getSessionFactory(IBusiness self);

}