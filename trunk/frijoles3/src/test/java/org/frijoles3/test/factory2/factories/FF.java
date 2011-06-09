package org.frijoles3.test.factory2.factories;

import org.frijoles3.FactoryBuilder;

public class FF implements IFF {

	public IAppF getAppFactory(final IFF self) {
		return FactoryBuilder.build(AppF.class);
	}

	public IBussinesF getBussinesFactory(final IFF self) {
		return FactoryBuilder.build(BussinesF.class);
	}

}
