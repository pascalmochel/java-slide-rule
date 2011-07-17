package org.frijoles3.test.impl;

import org.frijoles3.FactoryBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class InterOrImplTest {

	/**
	 * si la interface està anotada com a singleton, i la impl com a prototype,
	 * que sigui prototype: la sobrecàrrega de mètodes de factoria ha de
	 * funcionar
	 */
	@Test
	public void testname() throws Exception {

		final IF f = FactoryBuilder.build(F.class);
		f.getList(null);
		assertEquals("F: [getList={prototype}]", f.toString());
	}

	@Test
	public void testname2() throws Exception {

		final IF f = FactoryBuilder.build(F2.class);
		f.getList(null);
		assertEquals("F2: [getList={thread}[init:true]]", f.toString());
	}

}
