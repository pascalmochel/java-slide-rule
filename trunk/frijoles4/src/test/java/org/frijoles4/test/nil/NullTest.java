package org.frijoles4.test.nil;

import org.frijoles4.FrijolesContext;
import org.junit.Test;

import static org.junit.Assert.*;

public class NullTest {

	@Test
	public void testPrototype() {

		final FrijolesContext ctx = FrijolesContext.build(NullFactory.class);
		assertNull(ctx.getBean("null"));
	}

}
