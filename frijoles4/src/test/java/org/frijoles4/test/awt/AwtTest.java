package org.frijoles4.test.awt;

import java.awt.Frame;

import org.frijoles4.FrijolesContext;
import org.junit.Ignore;

@Ignore
@SuppressWarnings("unused")
public class AwtTest {

	public static void main(final String[] args) {

		final FrijolesContext ctx = FrijolesContext.build(AwtFactory.class);
		final Frame frame = (Frame) ctx.getBean("frame");
	}

}
