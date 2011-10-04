//package org.orm.session.identity;
//
//import org.junit.Test;
//import org.orm.record.Entity;
//import org.orm.test.ent.Dog;
//
//import static org.junit.Assert.*;
//
//public class IdentityMapTest {
//
//	@Test
//	public void testname() throws Exception {
//
//		final IdentityMap<Entity> i = new IdentityMap<Entity>();
//
//		final Dog d1 = (Dog) i.loadOrStore(new Dog(1, "din1", 7));
//		final Dog d2 = (Dog) i.loadOrStore(new Dog(1, "din2", 8));
//		final Dog d3 = (Dog) i.loadOrStore(new Dog(3, "din3", 9));
//
//		assertTrue(d1 == d2);
//		assertFalse(d1 == d3);
//		assertEquals("[ID_DOG=1, NAME=din1, AGE=7, [...]]", d2.toString());
//
//		i.store(new Dog(1, "din2", 8));
//		final Dog d4 = (Dog) i.loadOrStore(new Dog(1, "din3", 9));
//		assertEquals("[ID_DOG=1, NAME=din2, AGE=8, [...]]", d4.toString());
//	}
//
// }
