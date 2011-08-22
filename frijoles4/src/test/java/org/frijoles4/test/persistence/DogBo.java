package org.frijoles4.test.persistence;

public class DogBo implements IDogBo {

	protected DogDao dogDao;

	public DogBo(final DogDao dogDao) {
		super();
		this.dogDao = dogDao;
	}

	public void store(final Dog dog) {
		dogDao.store(dog);
	}

}
