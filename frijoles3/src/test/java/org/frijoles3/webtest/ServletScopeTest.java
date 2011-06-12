package org.frijoles3.webtest;

import org.frijoles3.FactoryBuilder;
import org.frijoles3.test.ents.Person;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServletScopeTest {

	@Test
	public void testname() throws Exception {

		final ServletRequestMock request = new ServletRequestMock();

		final IServletF f = FactoryBuilder.build(ServletF.class);

		final Person person1 = f.getPerson(null, request);
		final Person person2 = f.getPerson(null, request);

		assertTrue(request.getAttribute("getPerson") == person1);
		assertTrue(person1 == person2);
		assertEquals(person1.toString(), person2.toString());
		assertEquals("PersonImpl [dog=null, name=mhc]", person1.toString());
	}

}
