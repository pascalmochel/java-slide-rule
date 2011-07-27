package example.piclets;

import java.awt.Color;
import java.awt.Graphics;

import javax.servlet.http.HttpServletRequest;

import org.homs.piclet.plotter.Plotter;

import java.util.Date;

public class SecondsPiclet extends Plotter {

	private static final long serialVersionUID = 4878557061013469128L;

	public SecondsPiclet() {
		super(60, 10);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void draw(final HttpServletRequest request, final Graphics graphics) {

		final int ratio = new Date().getSeconds();

		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, super.xsize, super.ysize);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0, 0, super.xsize - 1, super.ysize - 1);
		graphics.fillRect(0, 0, ratio, super.ysize);
	}
}
