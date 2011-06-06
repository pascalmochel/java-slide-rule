package org.homs.slide.awt;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WL extends WindowAdapter {

	@Override
	public void windowClosing(final WindowEvent e) {
		((Frame) e.getComponent()).setVisible(false);
		System.exit(1);
	}

}