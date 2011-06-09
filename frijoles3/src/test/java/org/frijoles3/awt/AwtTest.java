package org.frijoles3.awt;

import java.awt.Frame;

import org.frijoles3.FactoryBuilder;
import org.junit.Ignore;

@Ignore
public class AwtTest {

	public static void main(final String[] args) {

		final IAwtFactory ctx = FactoryBuilder.build(AwtFactory.class);
		final Frame frame = ctx.getFrame(null);
		while (frame.isEnabled()) {

		}
		frame.setVisible(false);
	}

}
