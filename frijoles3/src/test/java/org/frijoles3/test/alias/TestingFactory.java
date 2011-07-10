package org.frijoles3.test.alias;

import org.frijoles3.anno.Scope;
import org.junit.Ignore;

@Ignore
public class TestingFactory implements ITestingFactory {

	@Scope
	public Long getResult(final ITestingFactory f) {
		return 5L;
	}

}