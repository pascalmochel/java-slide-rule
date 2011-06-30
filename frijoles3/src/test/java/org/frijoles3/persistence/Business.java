package org.frijoles3.persistence;

import javax.sql.DataSource;

import org.frijoles3.anno.Scope;
import org.frijoles3.aop.Intercept;
import org.frijoles3.aop.Interceptor;
import org.frijoles3.persistence.hibernate.HibernateSessionFactory;
import org.frijoles3.persistence.hibernate.TransactionalInterceptor;
import org.frijoles3.properties.PropertiesHolder;
import org.hibernate.SessionFactory;
import org.hsqldb.jdbc.jdbcDataSource;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public class Business implements IBusiness {

	protected PropertiesHolder props;

	public Business() {
		this.props = new PropertiesHolder("frijoles-hibernate");
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

		final LocalSessionFactoryBean r = new LocalSessionFactoryBean();
		r.setDataSource(self.getDataSource(X));
		r.setMappingResources(new String[] { ("./hbm/dog.hbm.xml") });
		r.setHibernateProperties(props);
		r.afterPropertiesSet();
		HibernateSessionFactory.setSessionFactory((SessionFactory) r.getObject());
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
