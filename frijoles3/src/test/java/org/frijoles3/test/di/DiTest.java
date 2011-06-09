package org.frijoles3.test.di;

import org.frijoles3.FactoryBuilder;
import org.frijoles3.test.ents.Person;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiTest {

	@Test
	public void testname1() throws Exception {

		final IDiFactory f = FactoryBuilder.build(DiFactory.class);

		assertEquals("PersonImpl [dog=DogImpl [name=din], name=mhc]", f.getPerson1(null).toString());

		final Person p1 = f.getPerson1(null);
		final Person p2 = f.getPerson1(null);

		assertTrue(p1 != p2);
		assertTrue(p1.getDog() == p2.getDog());
	}

	@Test
	public void testname2() throws Exception {

		final IDiFactory f = FactoryBuilder.build(DiFactory.class);

		assertEquals("PersonImpl [dog=DogImpl [name=turbo], name=arb]", f.getPerson2(null).toString());

		final Person p1 = f.getPerson2(null);
		final Person p2 = f.getPerson2(null);

		assertTrue(p1 != p2);
		assertTrue(p1.getDog() != p2.getDog());

	}

}