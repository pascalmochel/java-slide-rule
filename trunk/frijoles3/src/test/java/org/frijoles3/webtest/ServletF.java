package org.frijoles3.webtest;

import javax.servlet.ServletRequest;

import org.frijoles3.test.ents.Person;

public class ServletF implements IServletF {

	public Person getPerson(final IServletF self, final ServletRequest request) {
		final Person r = new Person();
		r.setName("mhc");
		return r;
	}

}
