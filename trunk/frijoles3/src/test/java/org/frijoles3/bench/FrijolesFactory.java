package org.frijoles3.bench;

import java.awt.Color;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Prototype;

import java.util.HashMap;
import java.util.Map;

public class FrijolesFactory implements IFrijolesFactory {

	@Scope(Prototype.class)
	public ColorsMap getColorsMap(final IFrijolesFactory f) {
		final Map<String, Color> map = new HashMap<String, Color>();

		map.put("red", f.getColorRed(null));
		map.put("green", f.getColorGreen(null));
		map.put("blue", f.getColorBlue(null));

		return new ColorsMap(map);
	}

	@Scope(Prototype.class)
	public Color getColorRed(final IFrijolesFactory f) {
		return new Color(255, 0, 0);
	}

	@Scope(Prototype.class)
	public Color getColorGreen(final IFrijolesFactory f) {
		return new Color(0, 255, 0);
	}

	@Scope(Prototype.class)
	public Color getColorBlue(final IFrijolesFactory f) {
		return new Color(0, 0, 255);
	}

}
