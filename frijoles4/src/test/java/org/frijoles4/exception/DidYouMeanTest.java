package org.frijoles4.exception;

import org.frijoles4.FrijolesContext;
import org.frijoles4.test.F1;
import org.junit.Test;

import static org.junit.Assert.*;

public class DidYouMeanTest {

	@Test
	public void testname() throws Exception {

		final FrijolesContext f = FrijolesContext.build(F1.class);
		try {
			f.getBean("grin");
		} catch (final Exception e) {
			assertEquals("alias not defined: : 'grin'; did you mean: 'green' ?", e.getMessage());
		}
	}

}
