package org.frijoles3.properties;

import javax.sql.DataSource;

import org.frijoles3.FactoryBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.*;

public class PropertiesTest {

	@Test
	public void testname() throws Exception {

		final IBusiness f = FactoryBuilder.build(Business.class);

		final DataSource ds = f.getDataSource(null);

		assertEquals("jdbc:hsqldb:file:/home/mhoms/hsqlzdb", ds.getConnection().getMetaData().getURL());

		ds.getConnection().prepareStatement(
		/**/"CREATE TABLE DOGS (ID_DOG NUMERIC(5), NAME VARCHAR(20));" +
		/**/"CREATE SEQUENCE DOGS_SEQ;").execute();

		//

		// final Session session = f.getSessionFactory(null).openSession();
		// final Transaction tx = session.beginTransaction();
		// try {
		// final Dog dog = new Dog(null, "chucho");
		//
		// assertNull(dog.getIdDog());
		// session.saveOrUpdate(dog);
		// assertNotNull(dog.getIdDog());
		//
		// tx.commit();
		// } catch (final Exception e) {
		// tx.rollback();
		// throw e;
		// }

		f.configureSessionFactory(null);

		final Session session = HibernateSessionFactory.getSession();
		final Transaction tx = session.beginTransaction();
		try {
			final Dog dog = new Dog(null, "chucho");

			assertNull(dog.getIdDog());
			session.saveOrUpdate(dog);
			assertNotNull(dog.getIdDog());

			tx.commit();
		} catch (final Exception e) {
			tx.rollback();
			throw e;
		}

		{
			final Dog dog = new Dog(null, "chucho");
			assertNull(dog.getIdDog());
			f.getDogBo(null).store(dog);
			assertNotNull(dog.getIdDog());
		}
	}

}
