package org.frijoles4.test;

import java.awt.Color;

import org.frijoles4.FrijolesContext;
import org.frijoles4.anno.Alias;
import org.frijoles4.anno.Scope;
import org.frijoles4.scope.impl.Prototype;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

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
		r[0] = ctx.getBean("blue");
		r[1] = ctx.getBean("green");
		r[2] = ctx.getBean("red");
		return r;
	}

	@Test
	public void testname() throws Exception {
		final FrijolesContext f = FrijolesContext.build(F1.class);
		assertEquals("java.awt.Color[r=0,g=0,b=255]", f.getBean("blue").toString());
		assertEquals("java.awt.Color[r=0,g=255,b=0]", f.getBean("green").toString());
		assertEquals("java.awt.Color[r=255,g=0,b=0]", f.getBean("red").toString());
		assertEquals("java.awt.Color[r=1,g=2,b=3]", f.getBean("custom", 1, 2, 3).toString());
		final Color[] colorsArray = f.getBean("colors-array");
		assertEquals(
				"[java.awt.Color[r=0,g=0,b=255], java.awt.Color[r=0,g=255,b=0], java.awt.Color[r=255,g=0,b=0]]",
				Arrays.toString(colorsArray));

		assertEquals(
				"[equals={singleton}, green={prototype}, class={singleton}, wait={singleton}, notify-all={singleton}, testname={singleton}, red={singleton}, hash-code={singleton}, colors-array={prototype}, blue={singleton}, notify={singleton}, custom={singleton}, to-string={singleton}]",
				f.toString());
	}

}
