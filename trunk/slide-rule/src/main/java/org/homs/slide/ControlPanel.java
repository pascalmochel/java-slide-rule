package org.homs.slide;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ControlPanel extends Panel implements ItemListener, ActionListener {

	private static final long serialVersionUID = 427067401599660412L;

	protected final CPanel slideRulePanel;

	protected final Button reset;
	protected final Checkbox trig;
	protected final Checkbox exp;

	public ControlPanel(final CPanel slideRulePanel) {
		super();
		this.slideRulePanel = slideRulePanel;

		this.setLayout(new GridLayout(1, 2));

		reset = new Button("Reset");
		reset.addActionListener(this);
		this.add(reset);

		trig = new Checkbox("trig", true);
		trig.addItemListener(this);
		this.add(trig);

		exp = new Checkbox("exp", true);
		exp.addItemListener(this);
		this.add(exp);
	}

	public void itemStateChanged(final ItemEvent e) {
		if (e.getSource().equals(trig)) {
			slideRulePanel.setTrigScales(e.getStateChange() == ItemEvent.SELECTED);
		} else if (e.getSource().equals(exp)) {
			slideRulePanel.setExpScales(e.getStateChange() == ItemEvent.SELECTED);
		}
	}

	public void actionPerformed(final ActionEvent e) {
		if (e.getSource().equals(reset)) {
			slideRulePanel.reset();
		}
	}

}
