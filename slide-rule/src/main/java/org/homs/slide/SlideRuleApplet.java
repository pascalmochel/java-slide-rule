package org.homs.slide;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class SlideRuleApplet extends Applet {

	private static final long serialVersionUID = 5292998892195620899L;

	protected CPanel panel = new CPanel();

	public SlideRuleApplet() {
		super();
		final int xsize = 1000;
		final int ysize = 355 + 40 + 100 + 40 + 22 + 30;

		this.setLayout(new BorderLayout());

		this.setBounds(0, 0, xsize, ysize);
		panel.setBounds(0, 0, xsize, ysize);

		this.setSize(xsize, ysize);
		panel.setSize(xsize, ysize);

		this.setPreferredSize(new Dimension(xsize, ysize));
		panel.setPreferredSize(new Dimension(xsize, ysize));

		final ControlPanel controlPanel = new ControlPanel(panel);
		this.add(controlPanel, "North");

		this.add(panel, "Center");

		panel.setVisible(true);
		this.setVisible(true);

		this.setBackground(Color.WHITE);
		repaint();
	}

	@Override
	public void init() {
		super.init();
		panel.repaint();
	}

}
