package org.frijoles3.test.ents;

public class Person {

	protected String name;
	protected Dog dog;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Dog getDog() {
		return dog;
	}

	public void setDog(final Dog dog) {
		this.dog = dog;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PersonImpl [dog=");
		builder.append(dog);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
