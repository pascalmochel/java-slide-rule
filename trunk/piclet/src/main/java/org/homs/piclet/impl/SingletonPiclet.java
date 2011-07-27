package org.homs.piclet.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;

public abstract class SingletonPiclet extends Piclet {

	private static final long serialVersionUID = 5708641800361384734L;

	protected String idSession;

	protected BufferedImage image;
	protected volatile boolean initializated;

	public SingletonPiclet(final int xsize, final int ysize, final String extension,
			final PicletImageType imageType) {
		super(xsize, ysize, extension, imageType);
		this.idSession = getClass().getName();
	}

	public SingletonPiclet(final int xsize, final int ysize, final String extension) {
		super(xsize, ysize, extension);
		this.idSession = getClass().getName();
	}

	public SingletonPiclet(final int xsize, final int ysize) {
		super(xsize, ysize);
		this.idSession = getClass().getName();
	}

	@Override
	protected BufferedImage getImage(final HttpServletRequest request) {

		if (!initializated) {
			synchronized (image) {
				if (!initializated) {
					final BufferedImage image = new BufferedImage(xsize, ysize, colorSpace);
					final Graphics graphics = image.getGraphics();
					draw(request, graphics);
					request.getSession().setAttribute(idSession, image);
					initializated = true;
					return this.image;
				}
			}
		}
		return this.image;

	}

}
