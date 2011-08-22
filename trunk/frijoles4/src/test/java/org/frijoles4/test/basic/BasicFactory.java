package org.frijoles4.test.basic;

import java.awt.Color;

import org.frijoles4.FrijolesContext;
import org.frijoles4.anno.Scope;
import org.frijoles4.scope.impl.Prototype;
import org.frijoles4.scope.impl.Singleton;

public class BasicFactory {

	@Scope(Singleton.class)
	public Color getBlue(final FrijolesContext ctx) {
		return new Color(0, 0, 255);
	}

	@Scope(Prototype.class)
	public Color getGreen(final FrijolesContext ctx) {
		return new Color(0, 255, 0);
	}

}
