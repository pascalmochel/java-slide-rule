package org.frijoles3.awt;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Prototype;

public interface IAwtFactory {

	@Scope
	Frame getFrame(IAwtFactory self);

	@Scope(Prototype.class)
	Rectangle getFrameRectangle(IAwtFactory self);

	@Scope(Prototype.class)
	Button getButton(IAwtFactory self);

	@Scope(Prototype.class)
	ActionListener getButtonListener(IAwtFactory self);

	@Scope
	WindowListener getFrameWindowListener(IAwtFactory self);

}
