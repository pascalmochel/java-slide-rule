package org.frijoles3.properties;

import javax.sql.DataSource;

public interface IBusiness {

	static final IBusiness X = null;

	DataSource getDataSource(IBusiness self);

	void configureSessionFactory(IBusiness self) throws Exception;

	DogBo getDogBo(final IBusiness self);

	DogDao getDogDao(IBusiness self);

}