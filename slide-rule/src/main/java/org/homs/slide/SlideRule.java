package org.homs.slide;

import java.awt.Color;

import org.homs.slide.awt.Gra;
import org.homs.slide.awt.Plot2;
import org.homs.slide.plotter.impl.k.Direction;
import org.homs.slide.plotter.impl.k.Label;
import org.homs.slide.plotter.impl.k.Size;
import org.homs.slide.scale.F;
import org.homs.slide.scale.impl.ABCD;
import org.homs.slide.scale.impl.CIDI;
import org.homs.slide.scale.impl.CT;
import org.homs.slide.scale.impl.K;
import org.homs.slide.scale.impl.L;
import org.homs.slide.scale.impl.LL0;
import org.homs.slide.scale.impl.LL1;
import org.homs.slide.scale.impl.LL2;
import org.homs.slide.scale.impl.LL3;
import org.homs.slide.scale.impl.R1;
import org.homs.slide.scale.impl.R2;
import org.homs.slide.scale.impl.S;
import org.homs.slide.scale.impl.ST;
import org.homs.slide.scale.impl.T;

import static java.lang.Math.*;

public class SlideRule {

	protected final int startx = 50;
	protected final double factor;
	protected final int width;

	protected final F abcd = new ABCD();
	protected final F cidi = new CIDI();
	protected final F L = new L();
	protected final F K = new K();
	protected final F R1 = new R1();
	protected final F R2 = new R2();
	protected final F S = new S();
	protected final F T = new T();
	protected final F ST = new ST();
	protected final F CT = new CT();
	protected final F ll0 = new LL0();
	protected final F ll1 = new LL1();
	protected final F ll2 = new LL2();
	protected final F ll3 = new LL3();

	private boolean plotTrigScales = true;
	private boolean plotExpScales = true;

	public SlideRule(final int width) {
		super();
		this.factor = width - 100;
		this.width = width;
	}

	public static Color lightGrey = new Color(0.9f, 0.9f, 0.9f);

	public void drawSlideRule(final Gra gra, final float displecementBar) {

		int y = 30;
		{
			// K
			gra.drawScaleLabel("K", "^3", startx, y);
			for (int i = 0; i < 1 * 3; i += 1) {
				final Plot2 p = new Plot2(factor, (float) (startx + i * factor / 3.0), y, Direction.UP);
				p.plot(gra, 1.0, 10.0, 1, K, Size.NORMAL, Label.INT);
				p.plot(gra, 1.0, 10.0, 0.5, K, Size.SMALL, Label.NONE);

				p.plot(gra, 1.0, 5.0, 0.1, K, Size.TINY, Label.NONE);
				p.plot(gra, 5.0, 10.0, 0.25, K, Size.TINY, Label.NONE);

				p.plot(gra, K, 1.5, Size.SMALL, "1.5");
			}
		}
		y += 35;
		{
			// A
			final Plot2 plotA = new Plot2(factor / 2.0, startx, y, Direction.UP);

			gra.drawScaleLabel("A", "", startx, y);
			plotA.plotPi(gra, abcd);
			plotA.plotE(gra, abcd);

			plotA.plot(gra, 1.0, 10.0, 1.0, abcd, Size.MAX, Label.INT);
			plotA.plot(gra, 10.0, 100.0, 10.0, abcd, Size.MAX, Label.INT);
			plotA.plot(gra, abcd, 1.5, Size.NORMAL, "1.5");
			plotA.plot(gra, abcd, 15, Size.NORMAL, "15");

			plotA.plot(gra, 1.0, 3.0, 0.1, abcd, Size.SMALL, Label.NONE);
			plotA.plot(gra, 1.0, 3.0, 0.05, abcd, Size.TINY, Label.NONE);

			plotA.plot(gra, 3.0, 6.0, 0.5, abcd, Size.SMALL, Label.NONE);
			plotA.plot(gra, 3.0, 6.0, 0.1, abcd, Size.TINY, Label.NONE);

			plotA.plot(gra, 6.0, 10.0, 0.2, abcd, Size.SMALL, Label.NONE);

			plotA.plot(gra, 10.0, 50.0, 1, abcd, Size.SMALL, Label.NONE);
			plotA.plot(gra, 10.0, 30.0, 0.5, abcd, Size.TINY, Label.NONE);

			plotA.plot(gra, 50.0, 100.0, 2, abcd, Size.TINY, Label.NONE);

		}
		gra.getG().setColor(Color.BLACK);
		gra.getG().drawLine(0, (y + 1), width, (y + 1));
		y += 2;

		final int bary1 = y;
		{
			// B
			gra.drawUScaleLabel("B", "", startx + displecementBar, y);

			final Plot2 plotB = new Plot2(factor / 2.0, startx + displecementBar, y, Direction.DOWN);

			plotB.plot(gra, 1.0, 10.0, 1.0, abcd, Size.MAX, Label.INT);
			plotB.plot(gra, 10.0, 100.0, 10.0, abcd, Size.MAX, Label.INT);
			plotB.plot(gra, abcd, 1.5, Size.NORMAL, Direction.DOWN, "1.5");
			plotB.plot(gra, abcd, 15, Size.NORMAL, Direction.DOWN, "15");

			plotB.plot(gra, 1.0, 3.0, 0.1, abcd, Size.SMALL, Label.NONE);
			plotB.plot(gra, 1.0, 3.0, 0.05, abcd, Size.TINY, Label.NONE);

			plotB.plot(gra, 3.0, 6.0, 0.5, abcd, Size.SMALL, Label.NONE);
			plotB.plot(gra, 3.0, 6.0, 0.1, abcd, Size.TINY, Label.NONE);

			plotB.plot(gra, 6.0, 10.0, 0.2, abcd, Size.SMALL, Label.NONE);

			plotB.plot(gra, 10.0, 50.0, 1, abcd, Size.SMALL, Label.NONE);
			plotB.plot(gra, 10.0, 30.0, 0.5, abcd, Size.TINY, Label.NONE);

			plotB.plot(gra, 50.0, 100.0, 2, abcd, Size.TINY, Label.NONE);

			plotB.plotUPi(gra, abcd);
			plotB.plotUE(gra, abcd);
		}
		y += 55;
		if (this.plotTrigScales) {
			{
				// S arcsin(x)
				gra.drawScaleLabel("S", "sin/cos", startx + displecementBar, y);
				final Plot2 p = new Plot2(factor, startx + displecementBar, y, Direction.UP);

				p.plotTwice(gra, 10, 45, 5, S, Size.MAX, Label.INT);
				p.plotTwice(gra, startx, 90, 15, S, Size.MAX, Label.INT);

				p.plot(gra, 6, startx, 1, S, Size.NORMAL, Label.NONE);
				p.plot(gra, startx, 90, 5, S, Size.NORMAL, Label.NONE);

				p.plot(gra, 6, startx, 0.5, S, Size.SMALL, Label.NONE);
				p.plot(gra, startx, 70, 1, S, Size.SMALL, Label.NONE);

				p.plot(gra, 5.8, 15, 0.1, S, Size.TINY, Label.NONE);

			}
			y += 1;
			gra.getG().setColor(Color.BLACK);
			gra.getG().drawLine((int) (startx + displecementBar), y,
					(int) (width - startx + displecementBar), y);
			y += 1;
			{
				// T arctan(x)
				gra.drawUScaleLabel("T", "tg/ctg", startx + displecementBar, y);
				final Plot2 p = new Plot2(factor, startx + displecementBar, y, Direction.DOWN);

				p.plotTwice(gra, 10, 45, 5, T, Size.NORMAL, Label.INT);
				p.plot(gra, 6, 45, 1, T, Size.SMALL, Label.NONE);
				p.plot(gra, 5.8, 45, 0.2, T, Size.TINY, Label.NONE);
			}
			y += 55;
			{
				// ST arcsin(x/100)
				gra.drawScaleLabel("ST", "sin/tg", startx + displecementBar, y);
				final Plot2 p = new Plot2(factor, startx + displecementBar, y, Direction.UP);

				p.plot(gra, 1, 5.0, 1, ST, Size.NORMAL, Label.INT);
				p.plot(gra, 1.5, 5.0, 1, ST, Size.NORMAL, Label.FLOAT);

				p.plot(gra, ST, 0.573, Size.TINY, Direction.UP, ".573");
				p.plot(gra, ST, 0.6, Size.NORMAL, Direction.UP, "0.6");
				p.plot(gra, ST, 5.74, Size.NORMAL, Direction.UP, "5.74");

				p.plot(gra, 0.6, 5.7, 0.1, ST, Size.SMALL, Label.NONE);
				p.plot(gra, 0.6, 5.7, 0.05, ST, Size.TINY, Label.NONE);

				p.plotPi(gra, ST);
			}
			y += 1;
			gra.getG().setColor(Color.BLACK);
			gra.getG().drawLine((int) (startx + displecementBar), y,
					(int) (width - startx + displecementBar), y);
			y += 1;
			{
				// CT arcsin(x/100)
				gra.drawUScaleLabel("CT", "cos", startx + displecementBar, y);
				final Plot2 p = new Plot2(factor, startx + displecementBar, y, Direction.DOWN);

				p.plot(gra, 85, 89, 1, CT, Size.NORMAL, Label.INT);
				p.plot(gra, 85.5, 89, 1, CT, Size.NORMAL, Label.FLOAT);

				p.plot(gra, CT, 84.3, Size.NORMAL, Direction.DOWN, "84.3");
				p.plot(gra, CT, 89.4, Size.NORMAL, Direction.DOWN, "89.4");

				p.plot(gra, 84.3, 89.4, 0.1, CT, Size.SMALL, Label.NONE);
				p.plot(gra, 84.3, 89.4, 0.05, CT, Size.TINY, Label.NONE);
			}
			y += 55;
		}
		{
			// CI
			gra.drawScaleLabel("CI", "10/x", startx + displecementBar, y);
			final Plot2 p = new Plot2(factor, (float) (factor + startx + displecementBar), y, Direction.UP);
			p.plot(gra, 1.0, 10.0, 1.0, cidi, Size.NORMAL, Label.INT);
			p.plot(gra, cidi, 1.5, Size.NORMAL, "1.5");
			p.plot(gra, cidi, 2.5, Size.NORMAL, "2.5");
			p.plot(gra, cidi, 3.5, Size.NORMAL, "3.5");
			p.plot(gra, cidi, 4.5, Size.NORMAL, "4.5");
			p.plot(gra, cidi, 5.5, Size.NORMAL, "5.5");

			p.plot(gra, 1.0, 10.0, 0.1, cidi, Size.SMALL, Label.NONE);
			p.plot(gra, 3.0, 5.0, 0.05, cidi, Size.TINY, Label.NONE);
			p.plot(gra, 1.0, 3.0, 0.02, cidi, Size.TINY, Label.NONE);
			p.plotPi(gra, cidi);
			p.plotE(gra, cidi);
		}
		y += 1;
		gra.getG().setColor(Color.BLACK);
		gra.getG().drawLine((int) (startx + displecementBar), y, (int) (width - startx + displecementBar), y);
		y += 1;
		{
			// CF (pi-folded)
			gra.drawUScaleLabel("CF", "", startx + displecementBar, y);

			Plot2 p = new Plot2(factor, (float) (startx - log10(abcd.yi(PI)) * factor) + displecementBar, y,
					Direction.DOWN);
			p.plot(gra, 4, 9, 1.0, abcd, Size.MAX, Label.INT);
			p.plot(gra, 3.5, 9.5, 1.0, abcd, Size.NORMAL, Label.FLOAT);
			p.plot(gra, 3.2, 9.9, 0.1, abcd, Size.SMALL, Label.NONE);
			p.plot(gra, 3.15, 5, 0.05, abcd, Size.TINY, Label.NONE);

			p.plotUPi(gra, abcd);

			p = new Plot2(factor, (float) (//
					startx //
							+ log10(abcd.yi(10)) * factor//
							- log10(abcd.yi(PI)) * factor//
					+ displecementBar), y, Direction.DOWN);
			p.plot(gra, 1.0, 3, 1.0, abcd, Size.NORMAL, Label.INT);
			p.plot(gra, 1.0, 3.1, 0.1, abcd, Size.SMALL, Label.NONE);
			p.plot(gra, 1.0, 3.10, 0.05, abcd, Size.TINY, Label.NONE);

			p.plotUPi(gra, abcd);
			p.plotUE(gra, abcd);

			p.plot(gra, abcd, 1.5, Size.NORMAL, Direction.DOWN, "1.5");
			p.plot(gra, abcd, 2.5, Size.NORMAL, Direction.DOWN, "2.5");

		}
		y += 55;
		{
			// C
			gra.drawScaleLabel("C", "", startx + displecementBar, y);
			final Plot2 p = new Plot2(factor, startx + displecementBar, y, Direction.UP);
			p.plot(gra, 1.0, 10.0, 1.0, abcd, Size.MAX, Label.INT);

			p.plot(gra, 1.5, 9.5, 1.0, abcd, Size.NORMAL, Label.FLOAT);
			p.plot(gra, 1.0, 10.0, 0.5, abcd, Size.NORMAL, Label.NONE);
			p.plot(gra, 1.0, 10.0, 0.1, abcd, Size.SMALL, Label.NONE);
			p.plot(gra, 1.0, 5.0, 0.05, abcd, Size.TINY, Label.NONE);

			p.plotPi(gra, abcd);
			p.plotE(gra, abcd);
		}
		y += 1;
		final int bary2 = y;
		gra.getG().setColor(Color.BLACK);
		gra.getG().drawLine(0, y, width, y);
		gra.getG().drawLine(startx, y, width - startx, y);
		y += 1;
		{
			// D
			gra.drawUScaleLabel("D", "", startx, y);
			final Plot2 p = new Plot2(factor, startx, y, Direction.DOWN);
			p.plot(gra, 1.0, 10.0, 1.0, abcd, Size.MAX, Label.INT);

			p.plot(gra, 1.5, 9.5, 1.0, abcd, Size.NORMAL, Label.FLOAT);
			p.plot(gra, 1.0, 10.0, 0.1, abcd, Size.SMALL, Label.NONE);
			p.plot(gra, 1.0, 5.0, 0.05, abcd, Size.TINY, Label.NONE);
			p.plotUPi(gra, abcd);
			p.plotUE(gra, abcd);
		}
		y += 55;
		{
			// DF (pi-folded)
			gra.drawScaleLabel("DF", "", startx, y);

			Plot2 p = new Plot2(factor, (float) (startx - log10(abcd.yi(PI)) * factor), y, Direction.UP);
			p.plot(gra, 4, 9, 1.0, abcd, Size.MAX, Label.INT);
			p.plot(gra, 3.5, 9.5, 1.0, abcd, Size.NORMAL, Label.FLOAT);
			p.plot(gra, 3.2, 9.9, 0.1, abcd, Size.SMALL, Label.NONE);
			p.plot(gra, 3.15, 5, 0.05, abcd, Size.TINY, Label.NONE);

			p.plotPi(gra, abcd);

			p = new Plot2(factor, (float) (//
					startx //
							+ log10(abcd.yi(10)) * factor//
					- log10(abcd.yi(PI)) * factor//
					), y, Direction.UP);
			p.plot(gra, 1.0, 3, 1.0, abcd, Size.NORMAL, Label.INT);
			p.plot(gra, 1.0, 3.1, 0.1, abcd, Size.SMALL, Label.NONE);
			p.plot(gra, 1.0, 3.10, 0.05, abcd, Size.TINY, Label.NONE);

			p.plotPi(gra, abcd);
			p.plotE(gra, abcd);

			p.plot(gra, abcd, 1.5, Size.NORMAL, Direction.UP, "1.5");
			p.plot(gra, abcd, 2.5, Size.NORMAL, Direction.UP, "2.5");

		}
		y += 1;
		gra.getG().setColor(Color.BLACK);
		gra.getG().drawLine(startx, y, (width - startx), y);
		y += 1;
		{
			// L
			gra.drawUScaleLabel("L", "log", startx, y);
			final Plot2 p = new Plot2(factor, startx, y, Direction.DOWN);
			p.plot(gra, 0.0, 1.0, 0.1, L, Size.MAX, Label.FLOAT);
			p.plot(gra, 0.0, 1.0, 0.05, L, Size.NORMAL, Label.NONE);
			p.plot(gra, 0.0, 1.0, 0.01, L, Size.SMALL, Label.NONE);

			p.plot(gra, 0.0, 1.0, 0.005, L, Size.TINY, Label.NONE);
		}
		y += 55;
		{
			// R1 sqrt
			gra.drawScaleLabel("R1", "√x", startx, y);
			final Plot2 p = new Plot2(factor, startx, y, Direction.UP);
			p.plot(gra, 1.0, sqrt(10.0), 0.5, R1, Size.MAX, Label.FLOAT);
			p.plot(gra, 1.0, sqrt(10.0), 0.1, R1, Size.NORMAL, Label.NONE);
			p.plot(gra, 1.0, sqrt(10.0), 0.05, R1, Size.SMALL, Label.NONE);
			p.plot(gra, 1.0, sqrt(4.0), 0.01, R1, Size.TINY, Label.NONE);
			p.plot(gra, sqrt(4.0), sqrt(10), 0.01, R1, Size.TINY, Label.NONE);

			p.plotPi(gra, R1);
		}
		y += 1;
		gra.getG().setColor(Color.BLACK);
		gra.getG().drawLine(startx, y, (width - startx), y);
		y += 1;
		{
			// R2 sqrt
			gra.drawUScaleLabel("R2", "√10x", startx, y);
			final Plot2 p = new Plot2(factor, startx, y, Direction.DOWN);
			p.plot(gra, 3.5, 10, 0.5, R2, Size.MAX, Label.FLOAT);
			p.plot(gra, 3.2, 10, 0.1, R2, Size.SMALL, Label.NONE);
			p.plot(gra, 3.15, 10, 0.05, R2, Size.TINY, Label.NONE);
		}
		if (plotExpScales) {
			y += 55;
			{
				// LL0
				gra.drawScaleLabel("LL0", "", startx, y);
				final Plot2 p = new Plot2(factor, startx, y, Direction.UP);
				p.plot(gra, 1.001, 1.01, 0.001, ll0, Size.NORMAL, Label.FLOAT);
				p.plot(gra, 1.001, 1.01, 0.0001, ll0, Size.SMALL, Label.NONE);
				p.plot(gra, 1.001, 1.005, 0.00005, ll0, Size.TINY, Label.NONE);
			}
			y += 25;
			{
				// LL1
				gra.drawScaleLabel("LL1", "", startx, y);
				final Plot2 p = new Plot2(factor, startx, y, Direction.UP);
				p.plot(gra, 1.01, 1.1, 0.01, ll1, Size.SMALL, Label.FLOAT);
				p.plot(gra, 1.01, 1.02, 0.005, ll1, Size.SMALL, Label.FLOAT);

				p.plot(gra, 1.01, 1.105, 0.005, ll1, Size.SMALL, Label.NONE);
				p.plot(gra, 1.01, 1.105, 0.001, ll1, Size.TINY, Label.NONE);
				p.plot(gra, 1.01, 1.05, 0.0005, ll1, Size.TINY, Label.NONE);
			}
			y += 25;
			{
				// LL2
				gra.drawScaleLabel("LL2", "", startx, y);
				final Plot2 p = new Plot2(factor, startx, y, Direction.UP);
				p.plot(gra, 1.2, 2.7, 0.1, ll2, Size.NORMAL, Label.FLOAT);
				p.plot(gra, 1.15, 1.2, 0.05, ll2, Size.NORMAL, Label.FLOAT);
				p.plot(gra, 1.11, 1.11, 0.05, ll2, Size.TINY, Label.FLOAT);

				p.plot(gra, 1.11, 1.9, 0.01, ll2, Size.SMALL, Label.NONE);
				p.plot(gra, 1.9, 2.7, 0.025, ll2, Size.SMALL, Label.NONE);

				p.plot(gra, 1.105, 1.5, 0.005, ll2, Size.TINY, Label.NONE);

				p.plot(gra, ll2, 1.105, "1.105");
			}
			y += 25;
			{
				// LL3
				gra.drawScaleLabel("LL3", "", startx, y);
				final Plot2 p = new Plot2(factor, startx, y, Direction.UP);
				p.plot(gra, 2.7, 2.9, 1, ll3, Size.TINY, Label.FLOAT);
				p.plot(gra, 3, 10, 1, ll3, Size.NORMAL, Label.INT);
				p.plot(gra, 10, 100, 10, ll3, Size.NORMAL, Label.INT);
				p.plot(gra, 200, 1000, 400, ll3, Size.NORMAL, Label.INT);
				p.plot(gra, 1000, 3000, 1000, ll3, Size.NORMAL, Label.NONE);
				p.plot(gra, 5000, 15000, 10000, ll3, Size.NORMAL, Label.NONE);

				p.plot(gra, ll3, 2000, "2K");
				p.plot(gra, ll3, 3000, "3K");
				p.plot(gra, ll3, 5000, "5K");
				p.plot(gra, ll3, 10000, "10K");
				p.plot(gra, ll3, 15000, "15");
				p.plot(gra, ll3, 20000, "20K");

				p.plot(gra, 3, 10, 0.5, ll3, Size.SMALL, Label.NONE);
				p.plot(gra, 10, 100, 5, ll3, Size.SMALL, Label.NONE);
				p.plot(gra, 200, 1000, 100, ll3, Size.SMALL, Label.NONE);
				p.plot(gra, 1000, 3000, 500, ll3, Size.SMALL, Label.NONE);
				p.plot(gra, 3000, 5000, 1000, ll3, Size.SMALL, Label.NONE);
				p.plot(gra, 5000, 15000, 5000, ll3, Size.SMALL, Label.NONE);

				p.plot(gra, 2.7, 7, 0.1, ll3, Size.TINY, Label.NONE);
				p.plot(gra, 7, 10, 0.25, ll3, Size.TINY, Label.NONE);
				p.plot(gra, 10, 40, 1, ll3, Size.TINY, Label.NONE);
				p.plot(gra, 40, 70, 2.5, ll3, Size.TINY, Label.NONE);
				p.plot(gra, 100, 600, 25, ll3, Size.TINY, Label.NONE);
				p.plot(gra, 1000, 5000, 250, ll3, Size.TINY, Label.NONE);
				p.plot(gra, 5000, 15000, 1000, ll3, Size.TINY, Label.NONE);

				p.plotPi(gra, ll3);
				p.plotE(gra, ll3);
			}
		}

		gra.getG().setColor(lightGrey);
		gra.getG().fillRect(0, bary1, (int) displecementBar, bary2 - bary1);
		final int a = (int) (startx + (factor));
		gra.getG().fillRect((int) (67 + a + displecementBar), bary1, 1000, bary2 - bary1);
	}

	public void setTrigScales(final boolean b) {
		this.plotTrigScales = b;
	}

	public void setExpScales(final boolean b) {
		this.plotExpScales = b;
	}

}
