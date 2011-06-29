package org.frijoles3.properties;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class DogBo {

	protected DogDao dogDao;

	public DogBo(final DogDao dogDao) {
		super();
		this.dogDao = dogDao;
	}

	public void store(final Dog dog) {

		final Session session = HibernateSessionFactory.getSession();
		final Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(dog);
			tx.commit();
		} catch (final Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		}
	}

}
