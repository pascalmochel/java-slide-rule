package org.homs.piclet.scope;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;

public interface IScopeWrapper {

	BufferedImage getImage(final HttpServletRequest request);

}
