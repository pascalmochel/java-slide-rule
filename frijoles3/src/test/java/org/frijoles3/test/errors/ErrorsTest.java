package org.frijoles3.test.errors;

import org.frijoles3.FactoryBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorsTest {

	@Test
	public void testname1() throws Exception {

		try {
			FactoryBuilder.build(IErrorsFactory.class);
			fail();
		} catch (final Exception e) {
			assertEquals(
					"factory must be a class, not an interface; offending object is interface org.frijoles3.test.errors.IErrorsFactory",
					e.getMessage());
		}
	}

	@Test
	public void testname2() throws Exception {

		try {
			@SuppressWarnings("unused")
			final ErrorsFactory1 f = FactoryBuilder.build(ErrorsFactory1.class);
			fail();
		} catch (final Exception e) {
			assertEquals("$Proxy cannot be cast to org.frijoles.test.errors.ErrorsFactory", e.getMessage()
					.replaceAll("\\d", ""));
		}
	}

	@Test
	public void testname3() throws Exception {

		try {
			FactoryBuilder.build(ErrorsFactory2.class);
			fail();
		} catch (final Exception e) {
			assertEquals(
					"cannot create class org.frijoles3.test.errors.ErrorsFactory2, it is visible, with a public default constructor?",
					e.getMessage());
		}
	}

	@Test
	public void testname4() throws Exception {

		try {
			final IErrorsFactory f = FactoryBuilder.build(ErrorsFactory.class);
			f.getValue1(null);
			fail();
		} catch (final Exception e) {
			assertEquals(
					"@Scope annotation not found in factory method: public abstract java.lang.Long org.frijoles3.test.errors.IErrorsFactory.getValue1(org.frijoles3.test.errors.IErrorsFactory)",
					e.getMessage());
		}
	}

}