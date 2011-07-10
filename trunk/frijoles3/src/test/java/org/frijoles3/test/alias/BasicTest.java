package org.frijoles3.test.alias;

import org.frijoles3.AliasedFactoryBuilder;
import org.frijoles3.FactoryBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasicTest {

	@Test
	public void testname1() throws Exception {

		final ITestingFactory f = FactoryBuilder.build(TestingFactory.class);

		assertEquals("TestingFactory: []", f.toString());
		assertEquals("5", f.getResult(null).toString());
		assertEquals("TestingFactory: [getResult={singleton}[init:true]]", f.toString());
	}

	@Test
	public void testname2() throws Exception {

		final ITestingFactory f = AliasedFactoryBuilder.build(TestingFactory.class);

		assertEquals("TestingFactory: [getResult={singleton}[init:false]]", f.toString());
		assertEquals("5", f.getResult(null).toString());
		assertEquals("TestingFactory: [getResult={singleton}[init:true]]", f.toString());
	}

}