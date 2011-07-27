package example.piclets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.servlet.http.HttpServletRequest;

import org.homs.piclet.impl.Piclet;

public class PercentPiclet extends Piclet {

	private static final long serialVersionUID = 4878557061013469128L;

	public PercentPiclet() {
		super(140, 10);
	}

	public PercentPiclet(final String extension) {
		super(100, 10, extension);
	}

	@Override
	protected void draw(final HttpServletRequest request, final Graphics graphics) {

		final int ratio = Integer.valueOf(request.getParameter("ratio"));

		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, super.xsize, super.ysize);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0, 0, 100 - 1, super.ysize - 1);
		graphics.fillRect(0, 0, ratio, super.ysize);

		final Font f = new Font("Arial", 1, 10);
		graphics.setFont(f);
		graphics.drawString(Integer.toString(ratio) + '%', 105, 8);
	}

}
