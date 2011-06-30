package org.frijoles3.persistence;

import org.frijoles3.persistence.hibernate.HibernateSessionFactory;
import org.hibernate.Session;

public class DogBo implements IDogBo {

	protected DogDao dogDao;

	public DogBo(final DogDao dogDao) {
		super();
		this.dogDao = dogDao;
	}

	public void store(final Dog dog) {
		final Session session = HibernateSessionFactory.getSession();
		session.saveOrUpdate(dog);
	}

}
