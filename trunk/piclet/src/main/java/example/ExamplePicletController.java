package example;

import org.homs.piclet.PicletController;
import org.homs.piclet.PicletsConfig;
import org.homs.piclet.plotter.IPlotter;

import example.piclets.ClockPiclet;
import example.piclets.PercentPiclet;

public class ExamplePicletController extends PicletController {

	private static final long serialVersionUID = 7374423578676646960L;

	@Override
	protected void registerPiclets(final PicletsConfig config) {

		final IPlotter percentPiclet = new PercentPiclet();
		final IPlotter secondsPiclet = new ClockPiclet();

		config
		/**/.defAsRequest("/bar.piclet", percentPiclet)

		/**/.defAsRequest("/seconds-bar.piclet", secondsPiclet)
		/**/.defAsSession("/seconds-session-bar.piclet", secondsPiclet)
		/**/.defAsSingleton("/seconds-singleton-bar.piclet", secondsPiclet)

		/**/.defAsRequest("/bar.png.piclet", "png", percentPiclet)
		/**/.defAsRequest("/bar.bmp.piclet", "bmp", percentPiclet)
		/**/.defAsRequest("/bar.gif.piclet", "gif", percentPiclet)
		/**/.defAsRequest("/bar.jpg.piclet", "jpg", percentPiclet);
	}

}
