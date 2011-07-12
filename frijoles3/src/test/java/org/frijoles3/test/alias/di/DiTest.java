//package org.frijoles3.test.alias.di;
//
//import org.frijoles3.AliasingFactoryBuilder;
//import org.frijoles3.FactoryBuilder;
//import org.frijoles3.FrijolesFactory;
//import org.frijoles3.test.ents.Person;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class DiTest {
//
//	@Test
//	public void testname1() throws Exception {
//
//		final FrijolesFactory f = (FrijolesFactory) AliasingFactoryBuilder.build(DiFactory.class);
//
//		// assertEquals("PersonImpl [dog=DogImpl [name=din], name=mhc]",
//		// f.getPerson1(null).toString());
//		assertEquals("PersonImpl [dog=DogImpl [name=din], name=mhc]", f.getBean("person1").toString());
//
//		// final Person p1 = f.getPerson1(null);
//		// final Person p2 = f.getPerson1(null);
//		final Person p1 = f.getBean("person1");
//		final Person p2 = f.getBean("person1");
//
//		System.out.println(f.toString());
//		assertTrue(p1 != p2);
//		assertTrue(p1.getDog() == p2.getDog());
//	}
//
//	@Test
//	public void testname2() throws Exception {
//
//		final FrijolesFactory f = (FrijolesFactory) AliasingFactoryBuilder.build(DiFactory.class);
//
//		assertEquals("PersonImpl [dog=DogImpl [name=turbo], name=arb]", f.getBean("person2").toString());
//
//		// final Person p1 = f.getPerson2(null);
//		// final Person p2 = f.getPerson2(null);
//		final Person p1 = f.getBean("person2");
//		final Person p2 = f.getBean("person2");
//
//		assertTrue(p1 != p2);
//		assertTrue(p1.getDog() != p2.getDog());
//
//	}
//
// }