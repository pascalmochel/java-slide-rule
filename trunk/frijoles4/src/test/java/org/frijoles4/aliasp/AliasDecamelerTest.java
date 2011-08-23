package org.frijoles4.aliasp;

import static org.junit.Assert.*;

import org.junit.Test;

public class AliasDecamelerTest {

	@Test
	public void testname() {
		AliasDecameler a = new AliasDecameler();
		assertEquals("id-dog", a.processAlias("idDog"));
		assertEquals("id-dog", a.processAlias("getIdDog"));
		assertEquals("id-a-b-c-dog", a.processAlias("idABCDog"));
	}

}
