//package org.frijoles3.test.alias;
//
//import org.frijoles3.FrijolesFactory;
//import org.frijoles3.AliasingFactoryBuilder;
//import org.frijoles3.FactoryBuilder;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class BasicTest {
//
//	/*
//	 * demostra que AliasedFactoryBuilder pot funcionar igual que
//	 * FactoryBuilder, però eagerly i traient instància per alias
//	 */
//
//	@Test
//	public void testname1() throws Exception {
//
//		final ITestingFactory f = FactoryBuilder.build(TestingFactory.class);
//
//		assertEquals("TestingFactory: []", f.toString());
//		assertEquals("5", f.getResult(null).toString());
//		assertEquals("TestingFactory: [getResult={singleton}[init:true]]", f.toString());
//	}
//
//	@Test
//	public void testname2() throws Exception {
//
//		final ITestingFactory f = AliasingFactoryBuilder.build(TestingFactory.class);
//
//		assertEquals("TestingFactory: [jou={singleton}[init:false], jou2={prototype}]", f.toString());
//		assertEquals("5", f.getResult(null).toString());
//		assertEquals("TestingFactory: [jou={singleton}[init:true], jou2={prototype}]", f.toString());
//	}
//
//	@Test
//	public void testname3() throws Exception {
//
//		final FrijolesFactory f = (FrijolesFactory) AliasingFactoryBuilder.build(TestingFactory.class);
//		final ITestingFactory f2 = (ITestingFactory) f;
//
//		assertEquals("TestingFactory: [jou={singleton}[init:false], jou2={prototype}]", f.toString());
//		assertEquals("5", f.getBean("jou").toString());
//
//		assertEquals("TestingFactory: [jou={singleton}[init:true], jou2={prototype}]", f.toString());
//		assertEquals("5", f.getBean("getResult").toString());
//
//		assertEquals("TestingFactory: [jou={singleton}[init:true], jou2={prototype}]", f.toString());
//		assertEquals("5", f2.getResult(null).toString());
//		assertEquals("TestingFactory: [jou={singleton}[init:true], jou2={prototype}]", f.toString());
//	}
//
//	@Test
//	public void testname4() throws Exception {
//
//		final FrijolesFactory f = (FrijolesFactory) AliasingFactoryBuilder.build(TestingFactory.class);
//		final ITestingFactory f2 = (ITestingFactory) f;
//
//		assertEquals("TestingFactory: [getResult/jou={singleton}[init:false], getResult2/jou2={prototype}]",
//				f.toString());
//		assertEquals("6", f.getBean("jou2").toString());
//		assertEquals("6", f.getBean("getResult2").toString());
//		assertEquals("6", f2.getResult2(null).toString());
//		assertEquals("TestingFactory: [getResult/jou={singleton}[init:false], getResult2/jou2={prototype}]",
//				f.toString());
//	}
//
//	@Test
//	public void testname5() throws Exception {
//
//		final FrijolesFactory f = (FrijolesFactory) AliasingFactoryBuilder.build(TestingFactory.class);
//
//		assertTrue(f.getBean("jou") == f.getBean("jou"));
//		assertTrue(f.getBean("jou2") != f.getBean("jou2"));
//		// assertEquals("TestingFactory: [getResult/jou={singleton}[init:false], getResult2/jou2={prototype}]",
//		// f.toString());
//	}
// }