package org.frijoles3.test.aop.factory;

import org.frijoles3.Deproxable;
import org.frijoles3.FactoryBuilder;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class AopTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Throwable {

		final IAopFactory ctx = FactoryBuilder.build(AopFactory.class);

		final List<String> list = ctx.getList(null);
		assertEquals("<mhc>", list.get(0));
		assertEquals("<arb>", list.get(1));

		Map<String, String> map = ctx.getMap(null);
		assertEquals("<mhc>", map.get("mhc"));
		assertEquals("<arb>", map.get("arb"));

		map = (Map<String, String>) ((Deproxable) map).deprox();
		assertEquals("mhc", map.get("mhc"));
		assertEquals("arb", map.get("arb"));
	}

}
