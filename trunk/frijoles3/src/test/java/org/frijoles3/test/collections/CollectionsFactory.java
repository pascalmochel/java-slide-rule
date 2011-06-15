package org.frijoles3.test.collections;

import org.frijoles3.anno.Scope;
import org.junit.Ignore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Ignore
public class CollectionsFactory implements ICollectionsFactory {

	@Scope
	public int getAge() {
		return 28;
	}

	@Scope
	public int[] getAgesArray() {
		return new int[] { 26, 28 };
	}

	@Scope
	public List<Integer> getAgesList() {
		return Arrays.asList(26, 28);
	}

	@Scope
	public Map<String, Integer> getAgesMap() {
		final Map<String, Integer> r = new HashMap<String, Integer>();
		r.put("a", 26);
		r.put("b", 28);
		return r;
	}

}
