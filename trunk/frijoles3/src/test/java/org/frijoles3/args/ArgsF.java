package org.frijoles3.args;

import org.frijoles3.test.ents.Person;

public class ArgsF implements IArgsF {

	public Person getPerson(final IArgsF self, final String name) {
		final Person r = new Person();
		r.setName(name);
		return r;
	}

}
