package org.frijoles3.test.deprox;

import org.frijoles3.Deproxable;
import org.frijoles3.FactoryBuilder;
import org.frijoles3.test.basic.ITestingFactory;
import org.frijoles3.test.basic.TestingFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeproxTest {

	@Test
	public void testname1() throws Exception {

		ITestingFactory f = FactoryBuilder.build(TestingFactory.class);
		assertEquals("[]", f.toString());

		f.getPerson(null);
		assertEquals("[getChucho={singleton}[init:true], getPerson={thread}[init:true]]", f.toString());

		f = (ITestingFactory) ((Deproxable) f).deprox();
		assertEquals("org.frijoles3.test.basic.TestingFactory@x", f.toString().replaceAll("\\@.+$", "@x"));
	}

}