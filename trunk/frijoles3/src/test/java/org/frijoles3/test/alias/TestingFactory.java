package org.frijoles3.test.alias;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Singleton;
import org.junit.Ignore;

@Ignore
public class TestingFactory implements ITestingFactory {

	@Scope(value = Singleton.class, alias = "jou")
	public Long getResult(final ITestingFactory f) {
		return 5L;
	}

}