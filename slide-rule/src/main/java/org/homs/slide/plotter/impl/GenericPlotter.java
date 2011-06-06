package org.homs.slide.plotter.impl;

import java.awt.Color;
import java.awt.Font;

import org.homs.slide.awt.Gra;
import org.homs.slide.plotter.impl.k.Direction;
import org.homs.slide.plotter.impl.k.Label;
import org.homs.slide.plotter.impl.k.Size;

public class GenericPlotter {

	protected GenericPlotter() {

	}

	public static void plot(final Gra gra, final float OX, final float OY, final double y, final double x,
			final double scale, final Size size, final Direction dir, final Label label) {

		gra.vline(x * scale + OX, OY, OY + dir.getDirection() * size.getSize());

		if (label.isShowLabel()) {
			gra.getG().setFont(new Font("Monospaced", Font.PLAIN, 9));
			gra.getG().setColor(Color.BLUE);
			final String lbl;
			if (label.isShowAsInt()) {
				lbl = String.valueOf((int) y);
			} else {
				lbl = String.valueOf((float) y);
			}
			int fonty = dir.getDirection() * size.getSize();
			if (dir.getDirection() == -1) {
				fonty -= 1;
			} else {
				fonty += 10;
			}
			gra.getG().drawString( //
					/**/lbl, //
					/**/(float) (x * scale + OX - 2), //
					/**/OY + fonty //
			);
		}
	}

	public static void plot(final Gra gra, final float OX, final float OY, final double y, final double x,
			final double scale, final Size size, final Direction dir, final String lbl) {

		gra.vline(x * scale + OX, OY, OY + dir.getDirection() * size.getSize());

		gra.getG().setFont(new Font("Monospaced", Font.PLAIN, 9));
		gra.getG().setColor(Color.BLUE);
		int fonty = dir.getDirection() * size.getSize();
		if (dir.getDirection() == -1) {
			fonty -= 1;
		} else {
			fonty += 10;
		}
		gra.getG().drawString( //
				/**/lbl, //
				/**/(float) (x * scale + OX - 2), //
				/**/OY + fonty //
		);
	}
}