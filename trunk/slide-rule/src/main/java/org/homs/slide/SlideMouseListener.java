package org.homs.slide;

import java.awt.Cursor;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SlideMouseListener implements MouseListener, MouseMotionListener {

	protected boolean pressed = false;
	protected Point p;
	protected boolean slow = false;
	// protected int mouseX = 200;

	protected float lastxSlideIncrement = 0.0f;
	protected float xSlideIncrement = 0.0f;

	protected float lastxCursorIncrement = 500.0f;
	protected float xCursorIncrement = 0.0f;

	protected Panel panel;
	private boolean isMovingCursor = false;

	public SlideMouseListener(final Panel panel) {
		super();
		this.panel = panel;
	}

	public void mouseClicked(final MouseEvent e) {
		// mouseX = e.getPoint().x;
		panel.repaint();
	}

	public void mouseEntered(final MouseEvent e) {
	}

	public void mouseExited(final MouseEvent e) {
	}

	public void mousePressed(final MouseEvent e) {
		pressed = true;
		p = e.getPoint();
		slow = e.getButton() == MouseEvent.BUTTON3;

		isMovingCursor = Math.abs(p.x - lastxCursorIncrement) < 50;

		final Cursor normalCursor = new Cursor(Cursor.MOVE_CURSOR);
		panel.setCursor(normalCursor);
	}

	public void mouseReleased(final MouseEvent e) {
		pressed = false;
		if (isMovingCursor) {
			lastxCursorIncrement = lastxCursorIncrement + xCursorIncrement;
			xCursorIncrement = 0.0f;
		} else {
			lastxSlideIncrement = lastxSlideIncrement + xSlideIncrement;
			xSlideIncrement = 0.0f;
		}
		final Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		panel.setCursor(normalCursor);
	}

	public void mouseDragged(final MouseEvent e) {
		final Point p2 = e.getPoint();
		if (isMovingCursor) {
			if (slow) {
				this.xCursorIncrement = (p2.x - p.x) / 20.0f;
			} else {
				this.xCursorIncrement = p2.x - p.x;
			}
		} else {
			if (slow) {
				this.xSlideIncrement = (p2.x - p.x) / 20.0f;
			} else {
				this.xSlideIncrement = p2.x - p.x;
			}
		}
		panel.repaint();
	}

	public void mouseMoved(final MouseEvent e) {
	}

	public float getSlideDisplecement() {
		return lastxSlideIncrement + xSlideIncrement;
	}

	public float getCursorDisplecement() {
		return lastxCursorIncrement + xCursorIncrement;
	}

	// public int getMouseX() {
	// return mouseX;
	// }

	// public void reset() {
	// this.lastxSlideIncrement = 0;
	// this.xSlideIncrement = 0;
	// this.lastxCursorIncrement = 100;
	// this.xCursorIncrement = 0;
	// }

}
