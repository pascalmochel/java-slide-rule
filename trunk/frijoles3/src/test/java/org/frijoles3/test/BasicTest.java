package org.frijoles3.test;

import org.frijoles3.FactoryBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasicTest {

	@Test
	public void testname1() throws Exception {

		final ITestingFactory f = FactoryBuilder.build(TestingFactory.class);

		assertEquals("15", f.getResult(null).toString());
		assertEquals("15", f.getResult(null).toString());

		assertEquals("[getOperand2{prototype}, getOperand1{prototype}, getResult{prototype}]", f.toString());
	}

	@Test
	public void testname2() throws Exception {

		final ITestingFactory f = FactoryBuilder.build(TestingFactory.class);

		assertTrue(f.getPrototypeBlueColor(null) != f.getPrototypeBlueColor(null));
		assertTrue(f.getSingleRedColor(null) == f.getSingleRedColor(null));

		assertEquals("[getSingleRedColor{singleton}[init=true], getPrototypeBlueColor{prototype}]", f
				.toString());
	}

}