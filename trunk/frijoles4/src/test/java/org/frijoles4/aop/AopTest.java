package org.frijoles4.aop;

import java.lang.reflect.Method;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AopTest {

	public Interceptor getInterceptor() {
		return new Interceptor() {
			public Object intercept(final Object targetBean, final Method method, final Object[] arguments)
					throws Exception {
				return '<' + method.invoke(targetBean, arguments).toString() + '>';
			}
		};
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testname() throws Exception {

		final List<String> i1 = new ArrayList<String>();
		i1.add("mhc");
		i1.add("arb");
		final List<String> i2 = Intercept.with(i1, getInterceptor());

		assertEquals("<mhc>", i2.get(0));
		assertEquals("<arb>", i2.get(1));

		final List<String> i3 = (List<String>) ((Deproxable) i2).deprox();
		assertEquals("mhc", i3.get(0));
		assertEquals("arb", i3.get(1));
	}

}
