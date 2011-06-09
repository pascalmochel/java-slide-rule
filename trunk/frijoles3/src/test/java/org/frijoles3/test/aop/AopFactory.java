package org.frijoles3.test.aop;

import org.frijoles3.aop.Interceptor;
import org.frijoles3.aop.MethodCall;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Ignore
public class AopFactory implements IAopFactory {

	public Interceptor getInterceptor(final IAopFactory self) {
		return new Interceptor() {
			public Object intercept(final MethodCall methodCall) {
				return '<' + methodCall.invoke().toString() + '>';
			}
		};
	}

	public List<String> getList(final IAopFactory self) {
		final List<String> r = new ArrayList<String>();
		r.add("mhc");
		r.add("arb");
		return r;
	}

	public Map<String, String> getMap(final IAopFactory self) {
		final Map<String, String> r = new HashMap<String, String>();
		r.put("mhc", "mhc");
		r.put("arb", "arb");
		return r;
	}

}
