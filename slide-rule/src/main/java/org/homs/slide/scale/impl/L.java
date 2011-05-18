package org.homs.slide.scale.impl;

import org.homs.slide.scale.F;

import static java.lang.Math.*;

public class L implements F {
	// public double y(final double x) {
	// return log10(x);
	// }

	public double yi(final double x) {
		return pow(10, x);
	}
}
