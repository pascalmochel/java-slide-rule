package org.homs.slide.scale.impl;

import org.homs.slide.scale.F;

import static java.lang.Math.*;

public class LL1 implements F {
	// public double y(final double x) {
	// return pow(10,x/10000);
	// }

	public double yi(final double x) {
		return 100 * log(x);
	}
}
