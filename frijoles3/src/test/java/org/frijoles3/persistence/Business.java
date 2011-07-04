package org.frijoles3.persistence;

import javax.sql.DataSource;

import org.frijoles3.anno.Scope;
import org.frijoles3.aop.Intercept;
import org.frijoles3.aop.Interceptor;
import org.frijoles3.persistence.hibernate.HibernateSessionFactory;
import org.frijoles3.persistence.hibernate.TransactionalInterceptor;
import org.frijoles3.properties.PropertiesLoader;
import org.hibernate.cfg.Configuration;
import org.hsqldb.jdbc.jdbcDataSource;

import java.util.Properties;

public class Business implements IBusiness {

	protected Properties props;

	public Business() {
		this.props = PropertiesLoader.load("frijoles-hibernate");
	}

	@Scope
	public DataSource getDataSource(final IBusiness self) {

		final jdbcDataSource ds = new jdbcDataSource();
		ds.setDatabase(props.getProperty("hsql-url"));
		ds.setUser(props.getProperty("hsql-user"));
		ds.setPassword(props.getProperty("hsql-pass"));
		return ds;
	}

	// TODO jaja, no hi ha problemes amb els retorns void!!! (deu passar null)
	@Scope
	public void configureSessionFactory(final IBusiness self) throws Exception {

		HibernateSessionFactory.setSessionFactory(//
				new Configuration()
				/**/.addProperties(props)
				/**/.addResource("./hbm/dog.hbm.xml")
				/**/.buildSessionFactory() //
				);
	}

	@Scope
	public DogDao getDogDao(final IBusiness self) {
		return new DogDao();
	}

	@Scope
	public Interceptor getTransactionalInterceptor(final IBusiness self) {
		return new TransactionalInterceptor();
	}

	@Scope
	public IDogBo getDogBo(final IBusiness self) {
		return Intercept.with(new DogBo(self.getDogDao(X)), self.getTransactionalInterceptor(X));
	}

}
