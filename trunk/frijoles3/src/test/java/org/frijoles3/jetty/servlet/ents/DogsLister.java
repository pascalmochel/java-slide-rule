package org.frijoles3.jetty.servlet.ents;

import java.util.List;

public class DogsLister {

	protected List<IDog> dogsList;

	public DogsLister(final List<IDog> dogsList) {
		super();
		this.dogsList = dogsList;
	}

	@Override
	public String toString() {
		return dogsList.toString();
	}

}
