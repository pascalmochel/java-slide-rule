package example.piclets;

import java.awt.Color;
import java.awt.Graphics;

import javax.servlet.http.HttpServletRequest;

import org.homs.piclet.plotter.Plotter;

import java.util.Date;
import static java.lang.Math.*;

public class ClockPiclet extends Plotter {

	private static final long serialVersionUID = 4878557061013469128L;

	public ClockPiclet() {
		super(20, 20);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void draw(final HttpServletRequest request, final Graphics graphics) {

		final int seconds = new Date().getSeconds();
		final double angle = (seconds * PI * 2f) / 60f - PI / 2f;

		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, xsize, ysize);
		graphics.setColor(Color.BLACK);
		graphics.drawOval(0, 0, xsize - 1, ysize - 1);

		final int radius = (xsize - 1) / 2;
		final int x = (int) (radius * cos(angle) + radius);
		final int y = (int) (radius * sin(angle) + radius);
		graphics.drawLine(radius, radius, x, y);
	}

}
