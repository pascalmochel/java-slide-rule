package org.frijoles4.test;

import java.awt.Color;

import org.frijoles4.FrijolesContext;
import org.frijoles4.anno.Alias;
import org.frijoles4.anno.Scope;
import org.frijoles4.scope.impl.Prototype;

public class F1 {

	public Color getBlue(final FrijolesContext ctx) {
		return new Color(0, 0, 255);
	}

	@Scope(Prototype.class)
	public Color getGreen(final FrijolesContext ctx) {
		return new Color(0, 255, 0);
	}

	@Alias("red")
	public Color getRedColor(final FrijolesContext ctx) {
		return new Color(255, 0, 0);
	}

	@Alias("custom")
	@Scope
	public Color getCustomColor(final FrijolesContext ctx, final int r, final int g, final int b) {
		return new Color(r, g, b);
	}

	@Alias("colors-array")
	@Scope(Prototype.class)
	public Color[] getColorsArray(final FrijolesContext ctx) {
		final Color[] r = new Color[3];
		r[0] = (Color) ctx.getBean("blue");
		r[1] = (Color) ctx.getBean("green");
		r[2] = (Color) ctx.getBean("red");
		return r;
	}

}
