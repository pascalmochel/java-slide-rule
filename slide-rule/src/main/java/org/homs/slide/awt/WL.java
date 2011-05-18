package org.homs.slide.awt;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WL implements WindowListener {

	public void windowActivated(final WindowEvent e) {
	}

	public void windowClosed(final WindowEvent e) {
	}

	public void windowClosing(final WindowEvent e) {
		((Frame) e.getComponent()).setVisible(false);
		System.exit(1);
	}

	public void windowDeactivated(final WindowEvent e) {
	}

	public void windowDeiconified(final WindowEvent e) {
	}

	public void windowIconified(final WindowEvent e) {
	}

	public void windowOpened(final WindowEvent e) {
	}

}