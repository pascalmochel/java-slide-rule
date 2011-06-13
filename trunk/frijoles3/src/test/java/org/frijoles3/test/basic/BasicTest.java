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

	// @Test
	// public void testname() throws Exception {
	//
	// final Color c = createProxy(Color.class);
	// }
	//
	// /**
	// * Create a proxy using NoOp callback. The target class must have a
	// default
	// * zero-argument constructor.
	// *
	// * @param targetClass the super class of the proxy
	// * @return a new proxy for a target class instance
	// */
	// @SuppressWarnings("unchecked")
	// public <T> T createProxy(final Class<T> targetClass) {
	// final Enhancer enhancer = new Enhancer();
	// enhancer.setSuperclass(targetClass);
	// enhancer.setCallback(NoOp.INSTANCE);
	// return (T) enhancer.create();
	// }

}