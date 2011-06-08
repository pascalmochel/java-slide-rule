package org.frijoles3.test.factory2.factories;

import java.awt.Color;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Singleton;

public interface IBussinesF {

	@Scope(Singleton.class)
	Color getColor(IBussinesF self);

}