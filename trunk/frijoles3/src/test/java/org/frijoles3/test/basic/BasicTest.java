package org.frijoles3.test.basic;

import org.frijoles3.FactoryBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasicTest {

	@Test
	public void testname1() throws Exception {

		final ITestingFactory f = FactoryBuilder.build(TestingFactory.class);

		assertEquals("15", f.getResult(null).toString());
		assertEquals("15", f.getResult(null).toString());

		assertEquals(
				"TestingFactory: [getOperand1={prototype}, getOperand2={prototype}, getResult={prototype}]",
				f.toString());
	}

	@Test
	public void testname2() throws Exception {

		final ITestingFactory f = FactoryBuilder.build(TestingFactory.class);

		assertTrue(f.getPrototypeBlueColor(null) != f.getPrototypeBlueColor(null));
		assertTrue(f.getSingleRedColor(null) == f.getSingleRedColor(null));

		assertEquals(
				"TestingFactory: [getPrototypeBlueColor={prototype}, getSingleRedColor={singleton}[init:true]]",
				f.toString());
	}

}