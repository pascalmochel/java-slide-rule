package org.frijoles4.test.persistence;

import org.frijoles4.FrijolesContext;
import org.frijoles4.test.persistence.hibernate.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.*;

public class HibernateTest {

	@Test
	public void testname() throws Exception {

		// final IBusiness f = FactoryBuilder.build(Business.class);
		final FrijolesContext ctx = FrijolesContext.build(BusinessFactory.class);

		ctx.getBean("configure-session-factory");

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
		} finally {
			session.close();
		}

		{
			final Dog dog = new Dog(null, "chucho2");
			assertNull(dog.getIdDog());
			final IDogBo bo = ctx.getBean("dog-bo");
			bo.store(dog);
			bo.store(dog);
			assertNotNull(dog.getIdDog());
		}
	}

}
