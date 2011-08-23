package org.frijoles4.test.jetty.servlet.ents;

import java.util.List;

public class DogsLister {

	protected List<DogImpl> dogsList;

	public DogsLister(final List<DogImpl> dogsList) {
		super();
		this.dogsList = dogsList;
	}

	@Override
	public String toString() {
		return dogsList.toString();
	}

}
