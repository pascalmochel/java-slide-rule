package org.frijoles3.test.alias;

import org.frijoles3.anno.Scope;
import org.junit.Ignore;

import java.util.HashMap;
import java.util.Map;

@Ignore
public class TestingFactory implements ITestingFactory {

	@Scope
	public Long getResult(final ITestingFactory f) {
		return new Long(5L);
	}

	public Map<String, Long> getAliasedBeans(final ITestingFactory self) {
		final Map<String, Long> r = new HashMap<String, Long>();
		r.put("result", self.getResult(null));
		return r;
	}

}