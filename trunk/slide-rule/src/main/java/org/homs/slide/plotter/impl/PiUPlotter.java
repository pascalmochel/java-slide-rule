package org.homs.slide.plotter.impl;

import java.awt.Color;
import java.awt.Font;

import org.homs.slide.awt.Gra;

public class PiUPlotter {

	public void plot(final Gra gra, final float OX, final float OY, final double y, final double x,
			final double scale, final String symbol) {

		gra.vline(x * scale + OX, OY + 5, OY + 15);

		gra.getG().setColor(Color.RED);
		gra.getG().setFont(new Font("Monospaced", Font.PLAIN, 10));
		gra.getG().drawString(symbol, (float) (x * scale + OX - 2), OY + 25);
	}
}