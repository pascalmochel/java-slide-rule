package org.frijoles3.test.collections;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Singleton;

import java.util.List;
import java.util.Map;

public interface ICollectionsFactory {

	@Scope(Singleton.class)
	int getAge(ICollectionsFactory f);

	@Scope(Singleton.class)
	int[] getAgesArray(ICollectionsFactory f);

	@Scope(Singleton.class)
	List<Integer> getAgesList(ICollectionsFactory f);

	@Scope(Singleton.class)
	Map<String, Integer> getAgesMap(ICollectionsFactory f);

}