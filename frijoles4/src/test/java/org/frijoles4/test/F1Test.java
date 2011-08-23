package org.frijoles4.test;

import java.awt.Color;

import org.frijoles4.FrijolesContext;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class F1Test {

	@Test
	public void testname() throws Exception {
		final FrijolesContext f = FrijolesContext.build(F1.class);
		assertEquals("java.awt.Color[r=0,g=0,b=255]", f.getBean("blue").toString());
		assertEquals("java.awt.Color[r=0,g=255,b=0]", f.getBean("green").toString());
		assertEquals("java.awt.Color[r=255,g=0,b=0]", f.getBean("red").toString());
		assertEquals("java.awt.Color[r=1,g=2,b=3]", f.getBean("custom").toString());
		final Color[] colorsArray = (Color[]) f.getBean("colors-array");
		assertEquals(
				"[java.awt.Color[r=0,g=0,b=255], java.awt.Color[r=0,g=255,b=0], java.awt.Color[r=255,g=0,b=0]]",
				Arrays.toString(colorsArray));

		assertEquals(
				"[blue={singleton}, colors-array={prototype}, custom={singleton}, green={prototype}, red={singleton}]",
				f.toString());
	}

}
