package org.homs.slide.plotter;

import org.homs.slide.awt.Gra;

public interface Plotter {
	void plot(Gra gra, final float OX, final float OY, final double y, final double x, final double scale);
}