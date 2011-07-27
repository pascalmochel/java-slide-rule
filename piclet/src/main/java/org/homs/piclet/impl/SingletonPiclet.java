package org.homs.piclet.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;

import org.homs.piclet.PicletImageType;

public abstract class SingletonPiclet extends Piclet {

	private static final long serialVersionUID = 5708641800361384734L;

	protected final Object mutex = new Object();
	protected BufferedImage image;
	protected volatile boolean initializated = false;

	public SingletonPiclet(final int xsize, final int ysize, final String extension,
			final PicletImageType imageType) {
		super(xsize, ysize, extension, imageType);
	}

	public SingletonPiclet(final int xsize, final int ysize, final String extension) {
		super(xsize, ysize, extension);
	}

	public SingletonPiclet(final int xsize, final int ysize) {
		super(xsize, ysize);
	}

	@Override
	protected BufferedImage getImage(final HttpServletRequest request) {

		if (!initializated) {
			synchronized (mutex) {
				if (!initializated) {
					this.image = new BufferedImage(xsize, ysize, colorSpace);
					final Graphics graphics = this.image.getGraphics();
					draw(request, graphics);
					initializated = true;
					return this.image;
				}
			}
		}
		return this.image;
	}

}
