package org.homs.slide;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.homs.slide.awt.Gra;

public class CPanel extends Panel {

	private static final long serialVersionUID = -2328572145303317548L;

	protected SlideRule slideRule;
	protected BufferedImage offscreen;

	protected final SlideMouseListener mouseListener;

	public CPanel() {
		super();

		this.mouseListener = new SlideMouseListener(this);
		this.addMouseListener(this.mouseListener);
		this.addMouseMotionListener(this.mouseListener);
	}

	// public void zoomIn() {
	// slideRule.zoomIn(mouseListener.getMouseX());
	// repaint();
	// }
	//
	public void reset() {
		mouseListener.reset();
		repaint();
	}

	@Override
	public void paint(final Graphics g) {

		final Rectangle dim = g.getClipBounds();
		final int width = dim.width;
		final int height = dim.height;

		if (slideRule == null) {
			slideRule = new SlideRule(width);
		}

		if (offscreen == null) {
			offscreen = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
			// createImage(getSize().width, getSize().height);
		}
		final Graphics og = offscreen.getGraphics();
		og.setColor(Color.WHITE);
		og.fillRect(0, 0, width, height);
		super.paint(og);

		// slide rule
		final Gra gra = new Gra(og);
		slideRule.drawSlideRule(gra, mouseListener.getSlideDisplecement());

		// final BufferedImage cursorImage = offscreen
		// .getSubimage(mouseListener.getMouseX() - 25, 0, 50, height);
		//
		// og.drawImage(cursorImage, mouseListener.getMouseX() - 50, 0, 100,
		// height, this);
		//
		// og.setColor(Color.BLACK);
		// og.drawLine(mouseListener.getMouseX() - 50, 0,
		// mouseListener.getMouseX() - 50, height);
		// og.drawLine(mouseListener.getMouseX() + 50, 0,
		// mouseListener.getMouseX() + 50, height);

		// cursor
		final int cursorX = (int) mouseListener.getCursorDisplecement();
		og.setColor(Color.RED);
		og.drawLine(cursorX, 0, cursorX, height);

		og.drawLine(cursorX + 50, 0, cursorX + 50, height);
		og.drawLine(cursorX - 50, 0, cursorX - 50, height);
		og.drawLine(cursorX + 51, 0, cursorX + 51, height);
		og.drawLine(cursorX - 51, 0, cursorX - 51, height);

		g.drawImage(offscreen, 0, 0, this);

		og.dispose();
	}

	@Override
	public void update(final Graphics g) {
		paint(g);
	}

}