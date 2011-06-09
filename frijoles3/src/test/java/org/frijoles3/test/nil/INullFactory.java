package org.frijoles3.test.nil;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Singleton;

public interface INullFactory {

	@Scope(Singleton.class)
	Integer getNull(INullFactory f);

}