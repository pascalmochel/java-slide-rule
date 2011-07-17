package org.frijoles3.test.errors;

import org.frijoles3.FactoryBuilder;
import org.frijoles3.exception.FrijolesException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorsTest {

	@Test
	public void testname1() throws Exception {

		try {
			FactoryBuilder.build(IErrorsFactory.class);
			fail();
		} catch (final FrijolesException e) {
			assertEquals(
					"factory must be a class, not an interface; offending object is interface org.frijoles3.test.errors.IErrorsFactory",
					e.getCause().getMessage());
		}
	}

	@Test
	public void testname2() throws Exception {

		try {
			@SuppressWarnings("unused")
			final ErrorsFactory1 f = FactoryBuilder.build(ErrorsFactory1.class);
			fail();
		} catch (final FrijolesException e) {
			assertEquals("object factory must implements almost one interface: ErrorsFactory1", e.getCause()
					.getMessage());
		}
	}

	@Test
	public void testname3() throws Exception {

		try {
			FactoryBuilder.build(ErrorsFactory2.class);
			fail();
		} catch (final FrijolesException e) {
			assertEquals("cannot instantiate class org.frijoles3.test.errors.ErrorsFactory2", e.getCause()
					.getMessage());
		}
	}

	@Test
	public void testname4() throws Exception {

		try {
			final IErrorsFactory f = FactoryBuilder.build(ErrorsFactory.class);
			f.getValue1(null);
			fail();
		} catch (final FrijolesException e) {
			assertEquals(
					"@Scope annotation not found in factory method: public abstract java.lang.Long org.frijoles3.test.errors.IErrorsFactory.getValue1(org.frijoles3.test.errors.IErrorsFactory)",
					e.getMessage());
		}
	}

	@Test
	public void testname5() throws Exception {

		try {
			final IErrorsFactory f = FactoryBuilder.build(ErrorsFactory.class);
			f.getValue2(null);
			fail();
		} catch (final FrijolesException e) {
			assertEquals(
					"building bean \"getValue2\" instance invoking: public abstract java.lang.Long org.frijoles3.test.errors.IErrorsFactory.getValue2(org.frijoles3.test.errors.IErrorsFactory)",
					e.getMessage());
		}
	}
}