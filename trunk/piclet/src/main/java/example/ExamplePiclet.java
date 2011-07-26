package example;

import java.awt.Color;
import java.awt.Graphics;

import javax.servlet.http.HttpServletRequest;

import org.homs.piclet.Piclet;

public class ExamplePiclet extends Piclet {

	private static final long serialVersionUID = 4878557061013469128L;

	public ExamplePiclet() {
		super(100, 20);
	}

	@Override
	protected void draw(final HttpServletRequest request, final Graphics graphics) {

		final int ratioParam = Integer.valueOf(request.getParameter("ratio"));
		final int ratio = (ratioParam * super.xsize) / 100;

		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, super.xsize, super.ysize);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0, 0, super.xsize - 1, super.ysize - 1);
		graphics.fillRect(0, 0, ratio, super.ysize);
	}

}