package org.homs.piclet.scope;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;

import org.homs.piclet.plotter.IPlotter;

public class RequestWrapper implements IScopeWrapper {

	protected final IPlotter plotter;

	public RequestWrapper(final IPlotter plotter) {
		super();
		this.plotter = plotter;
	}

	@Override
	public BufferedImage getImage(final HttpServletRequest request) {
		return plotter.getImage(request);
	}

}
