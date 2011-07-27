package example;

import org.homs.piclet.IPiclet;
import org.homs.piclet.Piclet;
import org.homs.piclet.PicletController;
import org.homs.piclet.plotter.Plotter;
import org.homs.piclet.scope.RequestWrapper;
import org.homs.piclet.scope.SessionWrapper;
import org.homs.piclet.scope.SingletonWrapper;

import example.piclets.PercentPiclet;
import example.piclets.SecondsPiclet;

import java.util.HashMap;
import java.util.Map;

public class ExamplePicletController extends PicletController {

	private static final long serialVersionUID = 7374423578676646960L;

	// TODO fer protected void registerPiclets(ConfigPiclet config) {
	@Override
	protected void registerPiclets(final Map<String, IPiclet> picletsMap) {
		picletsMap.put("/bar.piclet", new Piclet("png", new RequestWrapper(new PercentPiclet())));

		// ConfigPiclet.defAsRequest(new PercentPiclet())

		picletsMap.put("/seconds-bar.piclet", new Piclet("png", new RequestWrapper(new SecondsPiclet())));
		picletsMap.put("/seconds-session-bar.piclet", new Piclet("png", new SessionWrapper(
				new SecondsPiclet())));
		picletsMap.put("/seconds-singleton-bar.piclet", new Piclet("png", new SingletonWrapper(
				new SecondsPiclet())));

		picletsMap.put("/bar.bmp.piclet", new Piclet("bmp", new RequestWrapper(new PercentPiclet())));
		picletsMap.put("/bar.gif.piclet", new Piclet("gif", new RequestWrapper(new PercentPiclet())));
		picletsMap.put("/bar.jpg.piclet", new Piclet("jpg", new RequestWrapper(new PercentPiclet())));
	}

}

class ConfigPiclet {

	private static final String DEFAULT_EXTENSION = "png";

	protected final Map<String, IPiclet> picletsMap;

	private ConfigPiclet(final Map<String, IPiclet> picletsMap) {
		super();
		this.picletsMap = new HashMap<String, IPiclet>();
	}

	public ConfigPiclet defAsRequest(final String url, final String extension, final Plotter plotter) {
		this.picletsMap.put(url, new Piclet(extension, new RequestWrapper(plotter)));
		return this;
	}

	public ConfigPiclet defAsRequest(final String url, final Plotter plotter) {
		this.picletsMap.put(url, new Piclet(DEFAULT_EXTENSION, new RequestWrapper(plotter)));
		return this;
	}

}