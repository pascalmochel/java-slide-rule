package org.frijoles4.exception;

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
			/**/"AliasNotDefinedException: alias not defined: : 'age'; did you mean: 'null' ?\n",
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

}
