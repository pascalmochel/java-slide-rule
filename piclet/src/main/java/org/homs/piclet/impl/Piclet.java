package org.homs.piclet.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.homs.piclet.PicletImageType;

public abstract class Piclet implements Serializable {

	private static final long serialVersionUID = -6690958788857960498L;

	protected final String className;
	protected final int xsize;
	protected final int ysize;
	protected final int colorSpace;
	protected final String extension;
	protected final String autoGenName;

	public Piclet(final int xsize, final int ysize, final String extension, final PicletImageType imageType) {
		super();
		this.className = getClass().getSimpleName();
		this.xsize = xsize;
		this.ysize = ysize;
		this.extension = extension;
		this.colorSpace = imageType.getImageType();
		this.autoGenName = Integer.toHexString(System.identityHashCode(this)).toString();
	}

	public Piclet(final int xsize, final int ysize, final String extension) {
		this(xsize, ysize, extension, PicletImageType.RGB);
	}

	public Piclet(final int xsize, final int ysize) {
		this(xsize, ysize, "png");
	}

	public void doDownload(final HttpServletRequest request, final HttpServletResponse response) {

		try {

			final ByteArrayOutputStream picStream = getImageStream(request);

			String imageName = request.getParameter("name");
			if (imageName == null) {
				imageName = this.autoGenName;
			}

			final int imageLength = picStream.toByteArray().length;

			response.setContentType("image/" + extension);
			response.setContentLength(imageLength);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + imageName + "." + extension
					+ "\"");

			final ServletOutputStream op = response.getOutputStream();
			picStream.writeTo(op);

			picStream.flush();
			picStream.close();
			op.flush();
			op.close();

		} catch (final Exception e) {
			throw new RuntimeException("error downloading from " + className + ": ", e);
		}
	}

	protected ByteArrayOutputStream getImageStream(final HttpServletRequest request) throws IOException {

		try {

			final BufferedImage image = getImage(request);
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, extension, baos);
			return baos;

		} catch (final Exception e) {
			throw new RuntimeException("error rendering " + className + ": ", e);
		}
	}

	protected BufferedImage getImage(final HttpServletRequest request) {
		final BufferedImage image = new BufferedImage(xsize, ysize, colorSpace);
		final Graphics graphics = image.getGraphics();
		draw(request, graphics);
		return image;
	}

	protected abstract void draw(HttpServletRequest request, final Graphics graphics);

}
