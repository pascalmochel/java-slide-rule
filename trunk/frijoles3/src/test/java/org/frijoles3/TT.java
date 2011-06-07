package org.frijoles3;

import org.junit.Test;

import static org.junit.Assert.*;

public class TT {

	@Test
	public void testname1() throws Exception {

		final ITestingFactory f = FactoryBuilder.newInstance(TestingFactory.class);

		assertEquals("15", f.getResult(null).toString());
		assertEquals("15", f.getResult(null).toString());
	}

	@Test
	public void testname2() throws Exception {

		final ITestingFactory f = FactoryBuilder.newInstance(TestingFactory.class);

		assertTrue(f.getPrototypeBlueColor(null) != f.getPrototypeBlueColor(null));
		assertTrue(f.getSingleRedColor(null) == f.getSingleRedColor(null));
	}

}