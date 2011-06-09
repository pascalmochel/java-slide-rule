package org.frijoles3.test.aop;

import org.frijoles3.anno.InterceptBy;
import org.frijoles3.anno.Scope;
import org.frijoles3.aop.Interceptor;
import org.frijoles3.holder.impl.Prototype;
import org.frijoles3.holder.impl.Singleton;

import java.util.List;
import java.util.Map;

public interface IAopFactory {

	@Scope(Singleton.class)
	Interceptor getInterceptor(IAopFactory self);

	@Scope(Prototype.class)
	@InterceptBy("getInterceptor")
	List<String> getList(IAopFactory self);

	@Scope(Singleton.class)
	@InterceptBy("getInterceptor")
	Map<String, String> getMap(IAopFactory self);

}