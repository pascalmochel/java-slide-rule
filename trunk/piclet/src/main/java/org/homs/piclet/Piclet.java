package org.homs.piclet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.homs.piclet.exception.PicletException;
import org.homs.piclet.scope.IScopeWrapper;

import java.util.Arrays;
import java.util.Iterator;

public class Piclet implements IPiclet {

	protected final IScopeWrapper scopeWrapper;

	protected final String extension;
	protected final String autoGenName;

	public Piclet(final String extension, final IScopeWrapper scopeWrapper) {
		super();
		this.scopeWrapper = scopeWrapper;

		this.extension = extension;
		this.autoGenName = Integer.toHexString(System.identityHashCode(this)).toString();

		if (!canWriteFormat(extension)) {
			final String[] formatNames = ImageIO.getReaderFormatNames();
			throw new PicletException("formatName not supported: " + extension + "; availables are: "
					+ Arrays.toString(formatNames));
		}
	}

	@Override
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
			throw new PicletException("error downloading from request: " + request.getServletPath(), e);
		}
	}

	protected ByteArrayOutputStream getImageStream(final HttpServletRequest request) throws IOException {

		try {

			final BufferedImage image = this.scopeWrapper.getImage(request);
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, extension, baos);
			return baos;

		} catch (final Exception e) {
			throw new PicletException("error rendering for request: " + request.getServletPath(), e);
		}
	}

	public static boolean canWriteFormat(final String formatName) {
		final Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(formatName);
		return iter.hasNext();
	}

}
