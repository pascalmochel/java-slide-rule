package org.frijoles3.persistence;

import javax.sql.DataSource;

import org.frijoles3.aop.Interceptor;

public interface IBusiness {

	static final IBusiness X = null;

	DataSource getDataSource(IBusiness self);

	void configureSessionFactory(IBusiness self) throws Exception;

	IDogBo getDogBo(final IBusiness self);

	DogDao getDogDao(IBusiness self);

	Interceptor getTransactionalInterceptor(IBusiness self);

}