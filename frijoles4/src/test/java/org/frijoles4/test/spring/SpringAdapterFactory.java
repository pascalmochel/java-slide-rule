package org.frijoles4.test.spring;

import java.awt.Color;

import org.frijoles4.FrijolesContext;
import org.frijoles4.anno.Scope;
import org.frijoles4.scope.impl.Prototype;

public class SpringAdapterFactory {

	@Scope(Prototype.class)
	public Color getPrototypeBlueColor(final FrijolesContext ctx) {
		return new Color(255);
	}

	@Scope
	public Color getSingleRedColor(final FrijolesContext ctx) {
		return new Color(0);
	}
	
}
