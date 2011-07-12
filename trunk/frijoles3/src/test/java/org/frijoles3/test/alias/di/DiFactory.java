//package org.frijoles3.test.alias.di;
//
//import org.frijoles3.anno.Scope;
//import org.frijoles3.holder.impl.Prototype;
//import org.frijoles3.holder.impl.Singleton;
//import org.frijoles3.test.ents.Dog;
//import org.frijoles3.test.ents.Person;
//import org.junit.Ignore;
//
//@Ignore
//public class DiFactory implements IDiFactory {
//
//	@Scope(value = Prototype.class, alias = "person1")
//	public Person getPerson1(final IDiFactory self) {
//		final Person r = new Person();
//		r.setName("mhc");
//		r.setDog(self.getDog1(null));
//		return r;
//	}
//
//	@Scope(value = Singleton.class)
//	public Dog getDog1(final IDiFactory self) {
//		final Dog r = new Dog();
//		r.setName("din");
//		return r;
//	}
//
//	@Scope(value = Prototype.class, alias = "person2")
//	public Person getPerson2(final IDiFactory self) {
//		final Person r = new Person();
//		r.setName("arb");
//		r.setDog(self.getDog2(null));
//		return r;
//	}
//
//	@Scope(value = Prototype.class)
//	public Dog getDog2(final IDiFactory self) {
//		final Dog r = new Dog();
//		r.setName("turbo");
//		return r;
//	}
//
// }
