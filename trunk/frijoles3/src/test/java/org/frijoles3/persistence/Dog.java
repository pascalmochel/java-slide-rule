package org.frijoles3.persistence;

public class Dog {

	protected Long idDog;
	protected String name;

	public Dog(final Long idDog, final String name) {
		super();
		this.idDog = idDog;
		this.name = name;
	}

	public Long getIdDog() {
		return idDog;
	}

	public void setIdDog(final Long idDog) {
		this.idDog = idDog;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Dog [idDog=" + idDog + ", name=" + name + "]";
	}

}
