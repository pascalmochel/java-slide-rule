package org.homs.slide.awt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import static java.lang.Math.*;

public class Gra {

	protected final Graphics2D g;

	public Gra(final Graphics g) {
		super();
		this.g = (Graphics2D) g;
		// this.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		// this.g.setRenderingHint(RenderingHints.KEY_RENDERING,
		// RenderingHints.VALUE_RENDER_QUALITY);
		// this.g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		// RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	}

	public void setColorMono(final double v) {
		final float vf = (float) v;
		g.setColor(new Color(vf, vf, vf));
	}

	public void vline(final double x, final double y1, final double y2) {

		final double x1 = floor(x);
		final double x2 = ceil(x);

		// potència de color
		final double v1 = abs(x1 - x);
		final double v2 = abs(x2 - x);

		setColorMono(v1);
		g.drawLine((int) x1, (int) y1, (int) x1, (int) y2);
		setColorMono(v2);
		g.drawLine((int) x2, (int) y1, (int) x2, (int) y2);
	}

	public void drawScaleLabel(final String scaleLabel, final String funLabel, final float OX, final float OY) {
		// scale label
		g.setColor(Color.BLACK);
		g.setFont(new Font("Times New Roman", Font.BOLD, 13));
		g.drawString(scaleLabel, OX - 40, OY - 10);

		g.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		g.drawString(funLabel, OX - 20, OY - 10);
	}

	public void drawUScaleLabel(final String scaleLabel, final String funLabel, final float OX, final float OY) {
		// scale label
		g.setColor(Color.BLACK);
		g.setFont(new Font("Times New Roman", Font.BOLD, 13));
		g.drawString(scaleLabel, OX - 40, OY + 13);

		g.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		g.drawString(funLabel, OX - 20, OY + 13);
	}

	public Graphics2D getG() {
		return g;
	}

}