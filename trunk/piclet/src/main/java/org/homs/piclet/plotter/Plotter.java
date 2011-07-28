package org.homs.piclet.plotter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.servlet.http.HttpServletRequest;

import org.homs.piclet.PicletImageType;

public abstract class Plotter implements IPlotter {

	private static final long serialVersionUID = -6690958788857960498L;

	protected final int xsize;
	protected final int ysize;
	protected final PicletImageType imageType;

	public Plotter(final int xsize, final int ysize, final PicletImageType imageType) {
		super();
		this.xsize = xsize;
		this.ysize = ysize;
		this.imageType = imageType;
	}
	
	public Plotter(final int xsize, final int ysize) {
		this(xsize,ysize,PicletImageType.RGB);
	}

	public static boolean canWriteFormat(final String formatName) {
		final Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(formatName);
		return iter.hasNext();
	}

	@Override
	public BufferedImage getImage(final HttpServletRequest request) {
		final BufferedImage image = new BufferedImage(xsize, ysize, imageType.getImageType());
		final Graphics graphics = image.getGraphics();
		draw(request, graphics);
		return image;
	}

	protected abstract void draw(HttpServletRequest request, final Graphics graphics);

}
