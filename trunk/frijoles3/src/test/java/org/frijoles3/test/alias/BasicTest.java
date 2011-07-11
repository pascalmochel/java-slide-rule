package org.frijoles3.test.alias;

import org.frijoles3.AliasGetter;
import org.frijoles3.AliasedFactoryBuilder;
import org.frijoles3.EagerlyFactoryBuilder;
import org.frijoles3.FactoryBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasicTest {

	/*
	 * demostra que AliasedFactoryBuilder pot funcionar igual que
	 * FactoryBuilder, però eagerly
	 */

	@Test
	public void testname1() throws Exception {

		final ITestingFactory f = FactoryBuilder.build(TestingFactory.class);

		assertEquals("TestingFactory: []", f.toString());
		assertEquals("5", f.getResult(null).toString());
		assertEquals("TestingFactory: [getResult={singleton}[init:true]]", f.toString());
	}

	@Test
	public void testname2() throws Exception {

		final ITestingFactory f = EagerlyFactoryBuilder.build(TestingFactory.class);

		assertEquals("TestingFactory: [getResult/jou={singleton}[init:false]]", f.toString());
		assertEquals("5", f.getResult(null).toString());
		assertEquals("TestingFactory: [getResult/jou={singleton}[init:true]]", f.toString());
	}

	@Test
	public void testname3() throws Exception {

		final AliasGetter f = (AliasGetter) AliasedFactoryBuilder.build(TestingFactory.class);
		final ITestingFactory f2 = (ITestingFactory) f;

		assertEquals("TestingFactory: [getResult/jou={singleton}[init:false]]", f.toString());
		assertEquals("5", f.getBean("jou").toString());
		assertEquals("TestingFactory: [getResult/jou={singleton}[init:true]]", f.toString());
		assertEquals("5", f2.getResult(null).toString());
		assertEquals("TestingFactory: [getResult/jou={singleton}[init:true]]", f.toString());
	}

}