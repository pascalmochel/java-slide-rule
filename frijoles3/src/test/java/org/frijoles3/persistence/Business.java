package org.frijoles3.persistence;

import org.frijoles3.anno.Scope;
import org.frijoles3.aop.Intercept;
import org.frijoles3.aop.Interceptor;
import org.frijoles3.persistence.hibernate.HibernateSessionFactory;
import org.frijoles3.persistence.hibernate.TransactionalInterceptor;
import org.frijoles3.properties.PropertiesLoader;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class Business implements IBusiness {

	@Scope
	public Properties getHibernateProperties(final IBusiness self) {
		return PropertiesLoader.load("frijoles-hibernate");
	}

	// @Scope
	// public DataSource getDataSource(final IBusiness self) {
	//
	// final jdbcDataSource ds = new jdbcDataSource();
	// ds.setDatabase(props.getProperty("hibernate.connection.url"));
	// ds.setUser(props.getProperty("hibernate.connection.username"));
	// ds.setPassword(props.getProperty("hibernate.connection.password"));
	// return ds;
	// }

	// XXX jaja, no hi ha problemes amb els retorns void!!! (deu passar null)
	/**
	 * <pre>
	 * In order to have HSQLDB register itself, you need to access its 
	 * jdbcDriver class. You can do this the same way as in this example.
	 * 
	 * Class.forName("org.hsqldb.jdbcDriver");
	 * 
	 * It triggers static initialization of jdbcDriver class, which is:
	 * 
	 * static {
	 *     try {
	 *         DriverManager.registerDriver(new jdbcDriver());
	 *     } catch (Exception e) {}
	 * }
	 * </pre>
	 */
	@Scope
	public void configureSessionFactory(final IBusiness self) throws Exception {

		Class.forName("org.hsqldb.jdbcDriver");

		HibernateSessionFactory.setSessionFactory(//
				new Configuration()
				/**/.addProperties(self.getHibernateProperties(X))
				/**/.addResource("hbm/dog.hbm.xml")
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
		final DogBo bo = new DogBo(self.getDogDao(X));
		final Interceptor interceptor = self.getTransactionalInterceptor(X);
		return Intercept.with(bo, interceptor);
	}

}
