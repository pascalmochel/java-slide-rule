package org.homs.piclet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
	protected ByteArrayOutputStream getImageStream(final HttpServletRequest request) throws IOException {
		if (request.getSession().getAttribute(idSession) == null) {
			final ByteArrayOutputStream os = super.getImageStream(request);
			request.getSession().setAttribute(idSession, os);
		}
		return (ByteArrayOutputStream) request.getSession().getAttribute(idSession);
	}

}
