package org.frijoles3.properties;

import org.frijoles3.FactoryBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class PTest {

	@Test
	public void testname() throws Exception {

		try {
			FactoryBuilder.build(F.class).getSalute();
			fail();
		} catch (final Exception e) {
		}
		assertEquals("buenas", FactoryBuilder.build(F.class, IF.class).getSalute());
	}

}
