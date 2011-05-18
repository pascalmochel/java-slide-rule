package org.homs.slide.scale.impl;

import org.homs.slide.scale.F;

import static java.lang.Math.*;

public class K implements F {
	// public double y(final double x) {
	// return x * x * x;
	// }

	public double yi(final double x) {
		return cbrt(x);
	}
}
