package org.frijoles3.persistence;

import org.frijoles3.aop.Interceptor;

import java.util.Properties;

public interface IBusiness {

	static final IBusiness X = null;

	// DataSource getDataSource(IBusiness self);

	Properties getHibernateProperties(final IBusiness self);

	void configureSessionFactory(IBusiness self) throws Exception;

	IDogBo getDogBo(final IBusiness self);

	DogDao getDogDao(IBusiness self);

	Interceptor getTransactionalInterceptor(IBusiness self);

}