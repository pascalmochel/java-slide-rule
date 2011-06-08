package org.frijoles3.test.collections;

import org.frijoles3.FactoryBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CollectionsTest {

	@Test
	public void testname() throws Exception {

		final ICollectionsFactory ctx = FactoryBuilder.build(CollectionsFactory.class);

		assertEquals("28", String.valueOf(ctx.getAge(null)));
		assertEquals("[26, 28]", Arrays.toString(ctx.getAgesArray(null)));
		assertEquals("[26, 28]", ctx.getAgesList(null).toString());
		assertEquals("[26, 28]", sortedValueList(ctx.getAgesMap(null)).toString());

	}

	// TODO genial funció!
	public <K, V extends Comparable<? super V>> List<V> sortedValueList(final Map<K, V> map) {
		final List<V> sortedKeys = new ArrayList<V>(map.values());
		Collections.sort(sortedKeys);
		return sortedKeys;
	}

}
