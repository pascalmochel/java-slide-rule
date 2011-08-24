package org.frijoles4.test.collections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.frijoles4.FrijolesContext;
import org.frijoles4.anno.Scope;

public class CollectionsFactory  {

	@Scope
	public int getAge(FrijolesContext ctx) {
		return 28;
	}

	@Scope
	public int[] getAgesArray(FrijolesContext ctx) {
		return new int[] { 26, 28 };
	}

	@Scope
	public List<Integer> getAgesList(FrijolesContext ctx) {
		return Arrays.asList(26, 28);
	}

	@Scope
	public Map<String, Integer> getAgesMap(FrijolesContext ctx) {
		final Map<String, Integer> r = new HashMap<String, Integer>();
		r.put("a", 26);
		r.put("b", 28);
		return r;
	}

}
