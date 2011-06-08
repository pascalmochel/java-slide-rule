package org.frijoles3.test.factory2.factories;

import org.frijoles3.test.ents.Dog;
import org.frijoles3.test.ents.Person;

public class AppF implements IAppF {

	public Person getPerson(final IAppF self) {
		final Person r = new Person();
		r.setName("mhc");
		r.setDog(self.getDog(null));
		return r;
	}

	public Dog getDog(final IAppF self) {
		final Dog r = new Dog();
		r.setName("din");
		return r;
	}

}
