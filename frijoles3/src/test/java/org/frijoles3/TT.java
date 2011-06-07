package org.frijoles3;

import org.junit.Test;

import static org.junit.Assert.*;

public class TT {
	@Test
	public void testname() throws Exception {
		final ITestingFactory f = FactoryBuilder.newInstance(TestingFactory.class);
		assertEquals("15", f.getResult(null).toString());
		assertEquals("15", f.getResult(null).toString());
	}
}