package org.frijoles3.test.collections;

import org.junit.Ignore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Ignore
public class CollectionsFactory implements ICollectionsFactory {

	public int getAge(final ICollectionsFactory f) {
		return 28;
	}

	public int[] getAgesArray(final ICollectionsFactory f) {
		return new int[] { 26, 28 };
	}

	public List<Integer> getAgesList(final ICollectionsFactory f) {
		return Arrays.asList(26, 28);
	}

	public Map<String, Integer> getAgesMap(final ICollectionsFactory f) {
		final Map<String, Integer> r = new HashMap<String, Integer>();
		r.put("a", 26);
		r.put("b", 28);
		return r;
	}

}
