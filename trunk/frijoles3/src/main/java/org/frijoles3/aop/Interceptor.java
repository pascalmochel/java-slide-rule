package org.frijoles3.aop;

import java.lang.reflect.Method;

public interface Interceptor {

	Object intercept(Object targetBean, Method method, Object[] arguments) throws Exception;

}
