package org.frijoles3.bench;

import java.awt.Color;

import java.util.Map;

public class ColorsMap {

	protected final Map<String, Color> colors;

	public ColorsMap(final Map<String, Color> colors) {
		super();
		this.colors = colors;
	}

	public Map<String, Color> getColors() {
		return colors;
	}

	@Override
	public String toString() {
		String r = "this:{" + System.identityHashCode(this) + "}";
		for (final Color c : colors.values()) {
			r += System.identityHashCode(c) + ",";
		}
		return r;
	}

}