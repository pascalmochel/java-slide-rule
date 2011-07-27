package example;

import org.homs.piclet.IPiclet;
import org.homs.piclet.Piclet;
import org.homs.piclet.PicletController;
import org.homs.piclet.scope.RequestWrapper;
import org.homs.piclet.scope.SessionWrapper;
import org.homs.piclet.scope.SingletonWrapper;

import example.piclets.PercentPiclet;
import example.piclets.SecondsPiclet;
import example.piclets.SecondsSessionPiclet;
import example.piclets.SecondsSingletonPiclet;

import java.util.Map;

public class ExamplePicletController extends PicletController {

	private static final long serialVersionUID = 7374423578676646960L;

	@Override
	protected void registerPiclets(final Map<String, IPiclet> picletsMap) {
		picletsMap.put("/bar.piclet", new Piclet("png", new RequestWrapper(new PercentPiclet())));
		picletsMap.put("/seconds-bar.piclet", new Piclet("png", new RequestWrapper(new SecondsPiclet())));
		picletsMap.put("/seconds-session-bar.piclet", new Piclet("png", new SessionWrapper(
				new SecondsSessionPiclet())));
		picletsMap.put("/seconds-singleton-bar.piclet", new Piclet("png", new SingletonWrapper(
				new SecondsSingletonPiclet())));

		picletsMap.put("/bar.bmp.piclet", new Piclet("bmp", new RequestWrapper(new PercentPiclet())));
		picletsMap.put("/bar.gif.piclet", new Piclet("gif", new RequestWrapper(new PercentPiclet())));
		picletsMap.put("/bar.jpg.piclet", new Piclet("jpg", new RequestWrapper(new PercentPiclet())));
	}

}
