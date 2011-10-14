package org.orm.bo.service;

import org.orm.bo.dao.DogDao;
import org.orm.bo.dao.RabbitDao;
import org.orm.test.ent.Dog;

public class Service implements IService {

	protected final DogDao dogDao;
	protected final RabbitDao rabbitDao;

	public Service(final DogDao dogDao, final RabbitDao rabbitDao) {
		super();
		this.dogDao = dogDao;
		this.rabbitDao = rabbitDao;
	}

	public void storeDog(final Dog dog) {
		dogDao.store(dog);
	}

	public Dog loadDogById(final Integer id) {
		return dogDao.loadById(id);
	}

}
