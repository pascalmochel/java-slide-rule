package org.homs.piclet;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;

public abstract class SessionPiclet extends Piclet {

	private static final long serialVersionUID = 5708641800361384734L;

	protected String idSession;

	public SessionPiclet(final int xsize, final int ysize, final String extension, final int colorSpace) {
		super(xsize, ysize, extension, colorSpace);
		this.idSession = getClass().getName();
	}

	public SessionPiclet(final int xsize, final int ysize, final String extension) {
		super(xsize, ysize, extension);
		this.idSession = getClass().getName();
	}

	public SessionPiclet(final int xsize, final int ysize) {
		super(xsize, ysize);
		this.idSession = getClass().getName();
	}

	@Override
	protected BufferedImage getImage(final HttpServletRequest request) {
		if (request.getSession().getAttribute(idSession) == null) {
			final BufferedImage image = new BufferedImage(xsize, ysize, colorSpace);
			final Graphics graphics = image.getGraphics();
			draw(request, graphics);
			request.getSession().setAttribute(idSession, image);
			return image;
		}
		return (BufferedImage) request.getSession().getAttribute(idSession);
	}

}
