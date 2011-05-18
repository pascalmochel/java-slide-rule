package org.homs.slide.plotter.impl.k;

public enum Size {

	// TINY(3), SMALL(5), NORMAL(10), MAX(15);
	TINY(6), SMALL(9), NORMAL(12), MAX(15);

	protected final int size;

	private Size(final int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

}
