package org.frijoles4.test.basic;

import org.frijoles4.FrijolesContext;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasicFactoryTest {

	@Test
	public void testname1() throws Exception {
		final FrijolesContext f = FrijolesContext.build(BasicFactory.class);

		assertTrue(f.getBean("blue") == f.getBean("blue"));
		assertTrue(f.getBean("green") != f.getBean("green"));
	}

}
