package org.frijoles4.test.persistence;

import org.frijoles4.FrijolesContext;
import org.frijoles4.anno.Scope;
import org.frijoles4.aop.Intercept;
import org.frijoles4.aop.Interceptor;
import org.frijoles4.properties.PropertiesLoader;
import org.frijoles4.test.persistence.hibernate.HibernateSessionFactory;
import org.frijoles4.test.persistence.hibernate.TransactionalInterceptor;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class BusinessFactory {

	@Scope
	public Properties getHibernateProperties(final FrijolesContext ctx) {
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
	 * Class.forName(&quot;org.hsqldb.jdbcDriver&quot;);
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
	public void configureSessionFactory(final FrijolesContext ctx) throws Exception {

		Class.forName("org.hsqldb.jdbcDriver");

		HibernateSessionFactory.setSessionFactory(//
				new Configuration()
				/**/.addProperties((Properties) ctx.getBean("hibernate-properties"))
				/**/.addResource("hbm/dog.hbm.xml")
				/**/.buildSessionFactory() //
				);
	}

	@Scope
	public DogDao getDogDao(final FrijolesContext ctx) {
		return new DogDao();
	}

	@Scope
	public Interceptor getTransactionalInterceptor(final FrijolesContext ctx) {
		return new TransactionalInterceptor();
	}

	@Scope
	public IDogBo getDogBo(final FrijolesContext ctx) {
		final DogBo bo = new DogBo((DogDao) ctx.getBean("dog-dao"));
		final Interceptor interceptor = (Interceptor) ctx.getBean("transactional-interceptor");
		return Intercept.with(bo, interceptor);
	}

}
