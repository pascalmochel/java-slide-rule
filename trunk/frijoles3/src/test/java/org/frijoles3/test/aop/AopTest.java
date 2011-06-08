package org.frijoles3.test.aop;

import org.frijoles3.FactoryBuilder;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class AopTest {

	@Test
	public void test() throws Throwable {

		final IAopFactory ctx = FactoryBuilder.build(AopFactory.class);

		final List<String> list = ctx.getList(null);
		assertTrue(list != ctx.getList(null));
		assertEquals("<mhc>", list.get(0));
		assertEquals("<arb>", list.get(1));

		final Map<String, String> map = ctx.getMap(null);
		assertTrue(map == ctx.getMap(null));
		assertEquals("<mhc>", map.get("mhc"));
		assertEquals("<arb>", map.get("arb"));
	}

}
