package org.frijoles3.test.aop;

import java.lang.reflect.Method;

import org.frijoles3.aop.Intercept;
import org.frijoles3.aop.Interceptor;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Ignore
public class AopFactory implements IAopFactory {

	public Interceptor getInterceptor(final IAopFactory self) {
		return new Interceptor() {
			public Object intercept(final Object targetBean, final Method method, final Object[] arguments)
					throws Exception {
				return '<' + method.invoke(targetBean, arguments).toString() + '>';
			}
		};
	}

	public List<String> getList(final IAopFactory self) {
		final List<String> r = new ArrayList<String>();
		r.add("mhc");
		r.add("arb");
		return Intercept.with(r, self.getInterceptor(null));
	}

	public Map<String, String> getMap(final IAopFactory self) {
		final Map<String, String> r = new HashMap<String, String>();
		r.put("mhc", "mhc");
		r.put("arb", "arb");
		return Intercept.with(r, self.getInterceptor(null));
	}

}
