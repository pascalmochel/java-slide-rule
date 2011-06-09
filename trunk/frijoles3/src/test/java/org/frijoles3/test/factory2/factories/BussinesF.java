package org.frijoles3.test.factory2.factories;

import java.awt.Color;

public class BussinesF implements IBussinesF {

	public Color getColor(final IBussinesF self) {
		return new Color(1);
	}

}
