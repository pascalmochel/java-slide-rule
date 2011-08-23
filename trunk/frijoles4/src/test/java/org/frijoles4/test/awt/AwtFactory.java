package org.frijoles4.test.awt;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.frijoles4.FrijolesContext;
import org.frijoles4.anno.Scope;

public class AwtFactory {

	@Scope
	public Frame getFrame(final FrijolesContext ctx) {
		final Frame frame = new Frame();
		frame.setTitle("Frijoles AWT Test");

		frame.setLayout(new BorderLayout());
		frame.setBounds(ctx.getBean(Rectangle.class, "frame-rectangle"));
		frame.addWindowListener(ctx.getBean(WindowListener.class, "frame-window-listener"));

		frame.add(ctx.getBean(Component.class, "button"), BorderLayout.SOUTH);

		frame.setVisible(true);
		return frame;
	}

	@Scope
	public Rectangle getFrameRectangle(final FrijolesContext ctx) {
		return new Rectangle(300, 300, 400, 300);
	}

	@Scope
	public Button getButton(final FrijolesContext ctx) {
		final Button r = new Button("Close");
		r.addActionListener(ctx.getBean(ActionListener.class, "button-listener"));
		return r;
	}

	@Scope
	public ActionListener getButtonListener(final FrijolesContext ctx) {
		return new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				ctx.getBean(Frame.class, "frame").setEnabled(false);
				System.exit(1);
			}
		};
	}

	@Scope
	public WindowListener getFrameWindowListener(final FrijolesContext ctx) {
		return new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				e.getWindow().setEnabled(false);
				System.exit(1);
			}
		};
	}

}
