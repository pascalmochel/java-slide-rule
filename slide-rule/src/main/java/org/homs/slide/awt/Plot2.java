package org.homs.slide.awt;

import java.awt.Color;
import java.awt.Font;

import org.homs.slide.plotter.impl.GenericPlotter;
import org.homs.slide.plotter.impl.PiPlotter;
import org.homs.slide.plotter.impl.PiUPlotter;
import org.homs.slide.plotter.impl.k.Direction;
import org.homs.slide.plotter.impl.k.Label;
import org.homs.slide.plotter.impl.k.Size;
import org.homs.slide.scale.F;

import static java.lang.Math.*;

public class Plot2 {

	protected double scale;
	protected float OX;
	protected float OY;
	protected Direction direction;

	public Plot2(final double scale, final float oX, final float oY, final Direction direction) {
		super();
		this.scale = scale;
		OX = oX;
		OY = oY;
		this.direction = direction;
	}

	public void plot(final Gra gra, final double miny, final double maxy, final double step, final F fi,
			final Size size, final Label label) {

		for (double y = miny; y <= maxy; y += step) {
			final double x = log10(fi.yi(y));
			GenericPlotter.plot(gra, OX, OY, y, x, scale, size, direction, label);
		}
	}

	public void plotTwice(final Gra gra, final double miny, final double maxy, final double step, final F fi,
			final Size size, final Label label) {

		for (double y = miny; y <= maxy; y += step) {

			final double x = log10(fi.yi(y));

			GenericPlotter.plot(gra, OX, OY, y, x, scale, size, direction, Label.NONE);

			if (label.isShowLabel()) {

				int fonty = direction.getDirection() * size.getSize();
				if (direction.getDirection() == -1) {
					fonty -= 1;
				} else {
					fonty += 10;
				}

				gra.getG().setColor(Color.RED);
				gra.getG().setFont(new Font("Monospaced", Font.PLAIN, 9));
				gra.getG().drawString(String.valueOf((int) y), (float) (x * scale + OX - 2) - 8, OY + fonty);

				gra.getG().setColor(Color.BLUE);
				gra.getG().setFont(new Font("Monospaced", Font.PLAIN, 9));
				gra.getG().drawString(String.valueOf((int) Math.abs(90 - y)),
						(float) (x * scale + OX - 2) + 2, OY + fonty);
			}
		}
	}

	public void plotPi(final Gra gra, final F fi) {
		final double y = PI;
		final double x = log10(fi.yi(y));
		new PiPlotter().plot(gra, OX, OY, y, x, scale, "π");
	}

	public void plotUPi(final Gra gra, final F fi) {
		final double y = PI;
		final double x = log10(fi.yi(y));
		new PiUPlotter().plot(gra, OX, OY, y, x, scale, "π");
	}

	public void plotE(final Gra gra, final F fi) {
		final double y = E;
		final double x = log10(fi.yi(y));
		new PiPlotter().plot(gra, OX, OY, y, x, scale, "e");
	}

	public void plotUE(final Gra gra, final F fi) {
		final double y = E;
		final double x = log10(fi.yi(y));
		new PiUPlotter().plot(gra, OX, OY, y, x, scale, "e");
	}

	public void plot(final Gra gra, final F fi, final double atY, final String label) {
		final double y = atY;
		final double x = log10(fi.yi(y));
		GenericPlotter.plot(gra, OX, OY, y, x, scale, Size.MAX, Direction.UP, label);
	}

	public void plot(final Gra gra, final F fi, final double atY, final Size size, final String label) {
		final double y = atY;
		final double x = log10(fi.yi(y));
		GenericPlotter.plot(gra, OX, OY, y, x, scale, size, Direction.UP, label);
	}

	public void plot(final Gra gra, final F fi, final double atY, final Size size, final Direction dir,
			final String label) {
		final double y = atY;
		final double x = log10(fi.yi(y));
		GenericPlotter.plot(gra, OX, OY, y, x, scale, size, dir, label);
	}

	public void plotU(final Gra gra, final F fi, final double atY, final String label) {
		final double y = atY;
		final double x = log10(fi.yi(y));
		// new PiUPlotter().plot(gra, OX, OY, y, x, scale, label);
		GenericPlotter.plot(gra, OX, OY, y, x, scale, Size.MAX, Direction.DOWN, label);
	}
}