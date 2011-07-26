package org.homs.piclet;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Piclet extends HttpServlet {

	private static final long serialVersionUID = -6690958788857960498L;

	protected final int xsize;
	protected final int ysize;
	protected final int colorSpace;
	protected final String extension;

	public Piclet(final int xsize, final int ysize, final String extension, final int colorSpace) {
		super();
		this.xsize = xsize;
		this.ysize = ysize;
		this.extension = extension;
		this.colorSpace = colorSpace;
	}

	public Piclet(final int xsize, final int ysize, final String extension) {
		this(xsize, ysize, extension, BufferedImage.TYPE_INT_RGB);
	}

	public Piclet(final int xsize, final int ysize) {
		this(xsize, ysize, "png");
	}

	@Override
	protected void doGet(final HttpServletRequest arg0, final HttpServletResponse arg1)
			throws ServletException, IOException {
		doDownload(arg0, arg1);
	}

	@Override
	protected void doPost(final HttpServletRequest arg0, final HttpServletResponse arg1)
			throws ServletException, IOException {
		doDownload(arg0, arg1);
	}

	protected void doDownload(final HttpServletRequest request, final HttpServletResponse response) {

		try {

			final ByteArrayOutputStream picStream = getImageStream(request);

			final String imageName = request.getParameter("name");
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
			throw new RuntimeException(e);
		}
	}

	protected ByteArrayOutputStream getImageStream(final HttpServletRequest request) throws IOException {

		final BufferedImage image = new BufferedImage(xsize, ysize, colorSpace);
		final Graphics graphics = image.getGraphics();

		draw(request, graphics);
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, extension, baos);
		return baos;
	}

	protected abstract void draw(HttpServletRequest request, final Graphics graphics);

}
