package org.frijoles4.test.bench;

import java.awt.Color;

import org.frijoles4.FrijolesContext;
import org.frijoles4.anno.Alias;
import org.frijoles4.anno.Scope;
import org.frijoles4.scope.impl.Prototype;

import java.util.HashMap;
import java.util.Map;

public class Frijoles4Factory {

	@Alias("colors-map")
	@Scope(Prototype.class)
	public ColorsMap getColorsMap(final FrijolesContext ctx) {
		final Map<String, Color> map = new HashMap<String, Color>();

		map.put("red", (Color) ctx.getBean("red"));
		map.put("green", (Color) ctx.getBean("green"));
		map.put("blue", (Color) ctx.getBean("blue"));

		return new ColorsMap(map);
	}

	@Alias("red")
	@Scope(Prototype.class)
	public Color getColorRed(FrijolesContext ctx) {
		return new Color(255, 0, 0);
	}

	@Alias("green")
	@Scope(Prototype.class)
	public Color getColorGreen(FrijolesContext ctx) {
		return new Color(0, 255, 0);
	}

	@Alias("blue")
	@Scope(Prototype.class)
	public Color getColorBlue(FrijolesContext ctx) {
		return new Color(0, 0, 255);
	}

}
