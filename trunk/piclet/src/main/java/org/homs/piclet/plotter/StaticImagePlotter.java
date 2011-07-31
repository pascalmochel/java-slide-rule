package org.homs.piclet.plotter;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.homs.piclet.exception.PicletException;

public class StaticImagePlotter implements IPlotter {

	protected String fileName;

	public StaticImagePlotter(final String fileName) {
		super();
		this.fileName = fileName;
	}

	@Override
	public BufferedImage getImage(final HttpServletRequest request) {
		try {
			final String url = "http://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + fileName;
			final URL i = new URL(url);
			return ImageIO.read(i);
		} catch (final Exception e) {
			throw new PicletException("file not found: " + this.fileName, e);
		}
	}

}
