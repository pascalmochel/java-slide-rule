package org.frijoles3.aop;


public interface Interceptor {

	Object intercept(final MethodCall methodCall);

}
