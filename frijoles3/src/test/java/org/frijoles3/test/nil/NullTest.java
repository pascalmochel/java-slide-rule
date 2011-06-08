package org.frijoles3.test.nil;

import org.frijoles3.FactoryBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class NullTest {

	@Test
	public void testPrototype() {

		final INullFactory ctx = FactoryBuilder.build(NullFactory.class);

		final Integer nullb = ctx.getNull(null);
		assertNull(nullb);
	}

}
