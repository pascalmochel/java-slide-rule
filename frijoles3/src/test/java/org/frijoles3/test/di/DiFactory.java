package org.frijoles3.test.di;

import org.frijoles3.test.ents.Dog;
import org.frijoles3.test.ents.Person;
import org.junit.Ignore;

@Ignore
public class DiFactory implements IDiFactory {

	public Person getPerson1(final IDiFactory self) {
		final Person r = new Person();
		r.setName("mhc");
		r.setDog(self.getDog1(null));
		return r;
	}

	public Dog getDog1(final IDiFactory self) {
		final Dog r = new Dog();
		r.setName("din");
		return r;
	}

	public Person getPerson2(final IDiFactory self) {
		final Person r = new Person();
		r.setName("arb");
		r.setDog(self.getDog2(null));
		return r;
	}

	public Dog getDog2(final IDiFactory self) {
		final Dog r = new Dog();
		r.setName("turbo");
		return r;
	}
}
