package org.frijoles3.exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExceptionTest {

	@Test
	public void testname() throws Exception {

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
