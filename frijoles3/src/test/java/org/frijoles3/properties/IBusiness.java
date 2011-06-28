package org.frijoles3.properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;

public interface IBusiness {

	static final IBusiness X = null;

	DataSource getDataSource(IBusiness self);

	SessionFactory getSessionFactory(IBusiness self) throws Exception;

}