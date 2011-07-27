package org.homs.piclet.scope;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;

import org.homs.piclet.plotter.IPlotter;

public class SessionWrapper implements IScopeWrapper {

	protected final IPlotter plotter;

	protected String idSession;

	public SessionWrapper(final IPlotter plotter) {
		super();
		this.plotter = plotter;
		this.idSession = getClass().getName();
	}

	@Override
	public BufferedImage getImage(final HttpServletRequest request) {

		if (request.getSession().getAttribute(idSession) == null) {
			final BufferedImage image = plotter.getImage(request);
			request.getSession().setAttribute(idSession, image);
			return image;
		}
		return (BufferedImage) request.getSession().getAttribute(idSession);
	}

}
