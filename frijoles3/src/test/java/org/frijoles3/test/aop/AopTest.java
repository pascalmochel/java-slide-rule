package org.frijoles3.test.aop;

import java.lang.reflect.Method;

import org.frijoles3.aop.Intercept;
import org.frijoles3.aop.Interceptor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AopTest {

	@Test
	public void testTots() throws Exception {

		final Interceptor interceptor = new MyInterceptor();
		final IBean bean = Intercept.with(new Bean(), interceptor, ".*");

		bean.getOne();
		bean.findThree();

		assertEquals("[getOne, findThree]", interceptor.toString());
	}

	@Test
	public void testGets() throws Exception {

		final Interceptor interceptor = new MyInterceptor();
		final IBean bean = Intercept.with(new Bean(), interceptor, "get.*");

		bean.getOne();
		bean.findThree();

		assertEquals("[getOne]", interceptor.toString());
	}

	@Test
	public void testFinds() throws Exception {

		final Interceptor interceptor = new MyInterceptor();
		final IBean bean = Intercept.with(new Bean(), interceptor, "find.*");

		bean.getOne();
		bean.findThree();

		assertEquals("[findThree]", interceptor.toString());
	}

	@Test
	public void testNone() throws Exception {

		final Interceptor interceptor = new MyInterceptor();
		final IBean bean = Intercept.with(new Bean(), interceptor, "jou.*");

		bean.getOne();
		bean.findThree();

		assertEquals("[]", interceptor.toString());
	}

	static class MyInterceptor implements Interceptor {

		protected List<String> ms = new ArrayList<String>();

		public Object intercept(final Object targetBean, final Method method, final Object[] arguments)
				throws Exception {
			ms.add(method.getName());
			return method.invoke(targetBean, arguments);
		}

		@Override
		public String toString() {
			return ms.toString();
		}

	}
}
