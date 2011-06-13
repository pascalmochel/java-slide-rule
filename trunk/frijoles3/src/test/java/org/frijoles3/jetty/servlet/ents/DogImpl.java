package org.frijoles3.jetty.servlet.ents;

public class DogImpl implements IDog {

	protected String name;

	public DogImpl() {

	}

	public DogImpl(final String name) {
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
		return super.toString() + "[" + name + "]";
	}

}
