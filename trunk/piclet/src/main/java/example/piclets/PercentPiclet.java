package example.piclets;

import java.awt.Color;
import java.awt.Graphics;

import javax.servlet.http.HttpServletRequest;

import org.homs.piclet.Piclet;

public class PercentPiclet extends Piclet {

	private static final long serialVersionUID = 4878557061013469128L;

	public PercentPiclet() {
		super(100, 10);
	}

	@Override
	protected void draw(final HttpServletRequest request, final Graphics graphics) {

		final int ratioParam = Integer.valueOf(request.getParameter("ratio"));
		final int ratio = (ratioParam * super.xsize) / 100;

		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, super.xsize, super.ysize);
		graphics.setColor(Color.RED);
		graphics.drawRect(0, 0, super.xsize - 1, super.ysize - 1);
		graphics.fillRect(0, 0, ratio, super.ysize);
	}

}
