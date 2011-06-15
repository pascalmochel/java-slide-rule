package org.frijoles3.test.collections;

import java.util.List;
import java.util.Map;

public interface ICollectionsFactory {

	int getAge();

	int[] getAgesArray();

	List<Integer> getAgesList();

	Map<String, Integer> getAgesMap();

}