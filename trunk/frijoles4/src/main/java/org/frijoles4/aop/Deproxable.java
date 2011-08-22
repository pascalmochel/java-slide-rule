package org.frijoles4.aop;

public interface Deproxable {

	static final String DEPROX_METHOD_NAME = "deprox";

	Object deprox();
}
