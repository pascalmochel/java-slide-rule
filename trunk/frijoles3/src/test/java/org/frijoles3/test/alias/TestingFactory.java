package org.frijoles3.test.alias;

import org.frijoles3.anno.Scope;
import org.junit.Ignore;

@Ignore
public class TestingFactory implements ITestingFactory {

	@Scope(alias = "jou")
	public Long getResult(final ITestingFactory f) {
		return 5L;
	}

	public Long getResult2(final ITestingFactory f) {
		return 6L;
	}

}