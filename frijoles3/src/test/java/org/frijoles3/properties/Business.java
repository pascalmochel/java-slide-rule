package org.frijoles3.properties;

import javax.sql.DataSource;

import org.frijoles3.anno.Scope;
import org.hibernate.SessionFactory;
import org.hsqldb.jdbc.jdbcDataSource;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public class Business extends PropertiesHolder implements IBusiness {

	public Business() {
		super("frijoles-test");
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
	public DogBo getDogBo(final IBusiness self) {
		final DogBo bo = new DogBo(self.getDogDao(X));
		return bo;
	}

	@Scope
	public DogDao getDogDao(final IBusiness self) {
		return new DogDao();
	}

}
