package example;

import org.homs.piclet.PicletController;
import org.homs.piclet.PicletsConfig;
import org.homs.piclet.plotter.IPlotter;
import org.homs.piclet.plotter.StaticImagePlotter;

import example.piclets.PercentPlotter;
import example.piclets.SecondsPlotter;

public class ExamplePicletController extends PicletController {

	private static final long serialVersionUID = 7374423578676646960L;

	@Override
	protected void registerPiclets(final PicletsConfig config) {

		final IPlotter percentPlotter = new PercentPlotter();
		final IPlotter secondsPlotter = new SecondsPlotter();

		config
		/**/.defAsRequest("/bar.piclet", percentPlotter)

		/**/.defAsRequest("/seconds-bar.piclet", secondsPlotter)
		/**/.defAsSession("/seconds-session-bar.piclet", secondsPlotter)
		/**/.defAsSingleton("/seconds-singleton-bar.piclet", secondsPlotter)

		/**/.defAsRequest("/bar.png.piclet", "png", percentPlotter)
		/**/.defAsRequest("/bar.bmp.piclet", "bmp", percentPlotter)
		/**/.defAsRequest("/bar.gif.piclet", "gif", percentPlotter)
		/**/.defAsRequest("/bar.jpg.piclet", "jpg", percentPlotter)

		/**/.defAsRequest("/ladybug.piclet", "png", new StaticImagePlotter("/img/ladybug.jpg"))
		/**/;
	}

}
