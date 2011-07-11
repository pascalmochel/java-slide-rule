package org.frijoles3;

public interface FrijolesFactory {

	static final String GETTER_METHOD_NAME = "getBean";

	Object getBean(String aliasName);
}
