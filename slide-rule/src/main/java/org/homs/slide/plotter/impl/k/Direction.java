package org.homs.slide.plotter.impl.k;

public enum Direction {

	UP(true), DOWN(false);

	protected final int direction;

	private Direction(final boolean up) {
		this.direction = up ? -1 : 1;
	}

	public int getDirection() {
		return direction;
	}

}
