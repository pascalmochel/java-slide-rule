package org.frijoles3.awt;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AwtFactory implements IAwtFactory {

	public Frame getFrame(final IAwtFactory self) {
		final Frame frame = new Frame();
		frame.setTitle("Frijoles AWT Test");

		frame.setLayout(new BorderLayout());
		frame.setBounds(self.getFrameRectangle(null));
		frame.addWindowListener(self.getFrameWindowListener(null));

		frame.add(self.getButton(null), BorderLayout.SOUTH);

		frame.setVisible(true);
		return frame;
	}

	public Rectangle getFrameRectangle(final IAwtFactory self) {
		return new Rectangle(300, 300, 400, 300);
	}

	public Button getButton(final IAwtFactory self) {
		final Button r = new Button("Close");
		r.addActionListener(self.getButtonListener(null));
		return r;
	}

	public ActionListener getButtonListener(final IAwtFactory self) {
		return new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				self.getFrame(null).setEnabled(false);
			}
		};
	}

	public WindowListener getFrameWindowListener(final IAwtFactory self) {
		return new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				e.getWindow().setEnabled(false);
			}
		};
	}

}
