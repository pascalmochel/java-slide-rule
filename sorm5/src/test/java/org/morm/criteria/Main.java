package org.morm.criteria;

import static org.junit.Assert.*;
import static org.morm.criteria.Restrictions.*;

import org.junit.Test;

public class Main {

	@Test
	public void testname() throws Exception {
		assertEquals("id=? -- 2", eq("id", 2).toString());
		assertEquals("(name=? AND id=?) -- [arb, 2]", and(eq("name", "arb"), eq("id", 2)).toString());
		assertEquals("((name=? AND id=?) OR id=?) -- [arb, 2, 2]", or(and(eq("name", "arb"), eq("id", 2)),
				eq("id", 2)).toString());
	}

}
