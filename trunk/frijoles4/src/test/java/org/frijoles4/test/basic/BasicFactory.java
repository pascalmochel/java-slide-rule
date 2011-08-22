package org.frijoles4.test.basic;

import java.awt.Color;

import org.frijoles4.FrijolesContext;
import org.frijoles4.anno.Scope;
import org.frijoles4.scope.impl.Prototype;
import org.frijoles4.scope.impl.Singleton;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasicFactory {

	@Scope(Singleton.class)
	public Color getBlue(FrijolesContext ctx) {
		return new Color(0, 0, 255);
	}

	@Scope(Prototype.class)
	public Color getGreen(FrijolesContext ctx) {
		return new Color(0, 255, 0);
	}

	@Test
	public void testname1() throws Exception {
		FrijolesContext f = FrijolesContext.build(BasicFactory.class);

		assertTrue(f.getBean("blue") == f.getBean("blue"));
		assertTrue(f.getBean("green") != f.getBean("green"));
	}

}
