package org.frijoles3.properties;

import javax.sql.DataSource;

import org.frijoles3.anno.Scope;
import org.hsqldb.jdbc.jdbcDataSource;

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

	// /**
	// * <pre>
	// * <bean id="sessionFactory"
	// class="org.springframework.orm.hibernate3.LocalSessionFactoryBean">
	// * <property name="dataSource">
	// * <ref local="dataSource" />
	// * </property>
	// * <property name="mappingDirectoryLocations">
	// * <list>
	// * <value>classpath:../hbms</value>
	// * </list>
	// * </property>
	// * <property name="hibernateProperties">
	// * <props>
	// * <prop key="hibernate.dialect">${hibernate.dialect}</prop>
	// * <prop key="hibernate.show_sql">${hibernate.show_sql}</prop>
	// * <prop
	// key="hibernate.max_fetch_depth">${hibernate.max_fetch_depth}</prop>
	// * <prop key="hibernate.use_outer_join">${hibernate.use_outer_join}</prop>
	// * </props>
	// * </property>
	// * </bean>
	// * </pre>
	// */
	// @Scope
	// public SessionFactory getSessionFactory(final IBusiness self) {
	// final LocalSessionFactoryBean r = new LocalSessionFactoryBean();
	// r.setDataSource(self.getDataSource(X));
	// r.setMappingResources(new String[] { ("classpath:dog.hbm.xml") });
	// r.setHibernateProperties(props);
	// return (SessionFactory) r.getObject();
	// }

	// /**
	// * <pre>
	// * <bean id="transactionManager"
	// class="org.springframework.orm.hibernate3.HibernateTransactionManager">
	// * <property name="sessionFactory">
	// * <ref local="sessionFactory" />
	// * </property>
	// * </bean>
	// * </pre>
	// */
	// @Scope
	// public PlatformTransactionManager getTxmanager(final IBusiness self) {
	// final HibernateTransactionManager r = new HibernateTransactionManager();
	// r.setSessionFactory(self.getSessionFactory(X));
	// return r;
	// }
	//
	// /**
	// * <pre>
	// * <bean id="hibernateTemplate"
	// class="org.springframework.orm.hibernate3.HibernateTemplate">
	// * <property name="sessionFactory">
	// * <ref bean="sessionFactory" />
	// * </property>
	// * </bean>
	// * </pre>
	// */
	// @Scope
	// public HibernateTemplate getHibernateTemplate(final IBusiness self) {
	// final HibernateTemplate r = new HibernateTemplate();
	// r.setSessionFactory(self.getSessionFactory(X));
	// return r;
	// }

}
