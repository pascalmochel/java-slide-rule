package org.frijoles3.test.factory2.factories;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Singleton;

public interface IFF {

	@Scope(Singleton.class)
	IAppF getAppFactory(IFF self);

	@Scope(Singleton.class)
	IBussinesF getBussinesFactory(IFF self);

}