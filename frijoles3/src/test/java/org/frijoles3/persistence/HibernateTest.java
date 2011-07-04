package org.frijoles3.persistence;

import org.frijoles3.FactoryBuilder;
import org.frijoles3.persistence.hibernate.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.*;

public class HibernateTest {

	@Test
	public void testname() throws Exception {

		final IBusiness f = FactoryBuilder.build(Business.class);

		f.configureSessionFactory(null);

		final Session session = HibernateSessionFactory.getSession();
		final Transaction tx = session.beginTransaction();
		try {
			session.createSQLQuery("CREATE TABLE DOGS (ID_DOG NUMERIC(5), NAME VARCHAR(20));")
					.executeUpdate();
			session.createSQLQuery("CREATE SEQUENCE DOGS_SEQ;").executeUpdate();
			tx.commit();
		} catch (final Exception e) {
			tx.rollback();
			throw e;
		}

		{
			final Dog dog = new Dog(null, "chucho2");
			assertNull(dog.getIdDog());
			f.getDogBo(null).store(dog);
			assertNotNull(dog.getIdDog());
		}
	}

}
