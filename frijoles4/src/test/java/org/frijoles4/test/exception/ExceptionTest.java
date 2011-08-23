package org.frijoles4.test.exception;

import org.frijoles4.FrijolesContext;
import org.junit.Test;

public class ExceptionTest {

	@Test
	public void testname() throws Exception {
		FrijolesContext ctx = FrijolesContext.build(ExceptionFactory.class);

		try {
			ctx.getBean("age");
		} catch (Exception e) {
			e.printStackTrace();// TODO
		}
	}

}
