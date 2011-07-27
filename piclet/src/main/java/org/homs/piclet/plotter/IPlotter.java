package org.homs.piclet.plotter;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;

public interface IPlotter {

	BufferedImage getImage(final HttpServletRequest request);

}
