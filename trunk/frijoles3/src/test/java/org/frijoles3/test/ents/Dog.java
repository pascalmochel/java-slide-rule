package org.frijoles3.test.ents;

public class Dog {

	protected String name;

	public Dog() {

	}

	public Dog(final String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("DogImpl [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
