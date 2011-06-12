package org.frijoles3.test.args;

import org.frijoles3.FactoryBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArgsTest {

	@Test
	public void testname() throws Exception {

		final IArgsF f = FactoryBuilder.build(ArgsF.class);
		assertEquals("PersonImpl [dog=null, name=mhc]", f.getPerson().toString());
		assertEquals("PersonImpl [dog=null, name=mhc]", f.getPerson(null).toString());
		assertEquals("PersonImpl [dog=null, name=mhc]", f.getPerson(null, "mhc").toString());
	}

}
