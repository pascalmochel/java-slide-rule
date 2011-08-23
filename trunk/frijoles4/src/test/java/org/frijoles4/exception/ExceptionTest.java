package org.frijoles4.exception;

import java.lang.reflect.Method;

import org.frijoles4.FrijolesContext;
import org.frijoles4.exception.FrijolesException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExceptionTest {

	@Test
	public void testname1() throws Exception {
		final FrijolesContext ctx = FrijolesContext.build(ExceptionFactory.class);

		try {
			ctx.getBean("age");
		} catch (final FrijolesException e) {
			assertEquals(
			/**/"FrijolesException: error invoking a method context: "
			/**/+ "org.frijoles4.exception.ExceptionFactory#age(FrijolesContext);\n"
			/**/+ "[cause is:] NullPointerException: jou\n",
			/**/e.getBriefListTrace());
		}
	}

	@Test
	public void testname2() throws Exception {

		final String message = new FrijolesException("a", new Exception("b", new RuntimeException("c",
				new ClassNotFoundException("d")))).getBriefListTrace();
		assertEquals(""//
				+ "FrijolesException: a;\n" //
				+ "[cause is:] Exception: b;\n" //
				+ "[cause is:] RuntimeException: c;\n" //
				+ "[cause is:] ClassNotFoundException: d\n" //
		, message);
	}

	@Test
	public void testname() throws Exception {
		final Method m = ExceptionFactory.class.getMethod("age", FrijolesContext.class);
		assertEquals("org.frijoles4.exception.ExceptionFactory#age(FrijolesContext)",
		/**/ThrowableRenderer.renderFactoryMethodInfo(m));
	}

}
