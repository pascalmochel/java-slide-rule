package org.frijoles4.test.collections;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.frijoles4.FrijolesContext;
import org.junit.Test;

public class CollectionsTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testname() throws Exception {

		FrijolesContext ctx = FrijolesContext.build(CollectionsFactory.class);

		assertEquals("28", String.valueOf(ctx.getBean("age")));
		assertEquals("[26, 28]", Arrays.toString(ctx.getBean(int[].class,"ages-array")));
		assertEquals("[26, 28]", ctx.getBean("ages-list").toString());
		assertEquals("[26, 28]", sortedValueList(ctx.getBean(Map.class,"ages-map")).toString());
	}

	// TODO genial funció!
	public <K, V extends Comparable<? super V>> List<V> sortedValueList(final Map<K, V> map) {
		final List<V> sortedKeys = new ArrayList<V>(map.values());
		Collections.sort(sortedKeys);
		return sortedKeys;
	}

}
