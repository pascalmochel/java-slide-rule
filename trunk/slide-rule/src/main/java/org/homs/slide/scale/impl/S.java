package org.homs.slide.scale.impl;

import org.homs.slide.scale.F;

import static java.lang.Math.*;

public class S implements F {
	// public double y(final double x) {
	// return (360.0 / (2.0 * PI)) * asin(x / 10.0);
	// }

	public double yi(final double x) {
		return 10 * sin((2.0 * PI * x) / 360.0);
	}
}
