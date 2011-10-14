package org.orm.bo.service;

import org.orm.test.ent.Dog;

public interface IService {

	void storeDog(final Dog dog);

	Dog loadDogById(final Integer id);

}