package org.homs.piclet.plotter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.servlet.http.HttpServletRequest;

import org.homs.piclet.PicletImageType;

import java.util.Arrays;
import java.util.Iterator;

public abstract class Plotter implements IPlotter {

	private static final long serialVersionUID = -6690958788857960498L;

	protected final int xsize;
	protected final int ysize;
	protected final int colorSpace;

	public Plotter(final int xsize, final int ysize, final String extension, final PicletImageType imageType) {
		super();
		this.xsize = xsize;
		this.ysize = ysize;
		this.colorSpace = imageType.getImageType();

		if (!canWriteFormat(extension)) {
			final String[] formatNames = ImageIO.getReaderFormatNames();
			System.out.println(Arrays.toString(formatNames));
			throw new RuntimeException("formatName not supported: " + extension + "; availables are: "
					+ Arrays.toString(formatNames));
		}
	}

	public Plotter(final int xsize, final int ysize, final String extension) {
		this(xsize, ysize, extension, PicletImageType.RGB);
	}

	public Plotter(final int xsize, final int ysize) {
		this(xsize, ysize, "png");
	}

	public static boolean canWriteFormat(final String formatName) {
		final Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(formatName);
		return iter.hasNext();
	}

	@Override
	public BufferedImage getImage(final HttpServletRequest request) {
		final BufferedImage image = new BufferedImage(xsize, ysize, colorSpace);
		final Graphics graphics = image.getGraphics();
		draw(request, graphics);
		return image;
	}

	protected abstract void draw(HttpServletRequest request, final Graphics graphics);

}
