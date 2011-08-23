package org.frijoles4.test.properties;

import static org.junit.Assert.*;

import org.frijoles4.FrijolesContext;
import org.junit.Test;

public class PropsTest {

	@Test
	public void testname() throws Exception {

		FrijolesContext ctx = FrijolesContext.build(PropsFactory.class);
		assertEquals("buenas", ctx.getBean("salute"));
	}

}
