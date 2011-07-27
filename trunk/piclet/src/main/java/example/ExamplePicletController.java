package example;

import org.homs.piclet.Piclet;
import org.homs.piclet.PicletController;

import example.piclets.PercentPiclet;
import example.piclets.SecondsPiclet;
import example.piclets.SecondsSessionPiclet;

import java.util.Map;

public class ExamplePicletController extends PicletController {

	private static final long serialVersionUID = 7374423578676646960L;

	@Override
	protected void registerPiclets(final Map<String, Piclet> picletsMap) {
		picletsMap.put("/bar.piclet", new PercentPiclet());
		picletsMap.put("/seconds-bar.piclet", new SecondsPiclet());
		picletsMap.put("/seconds-session-bar.piclet", new SecondsSessionPiclet());
	}

}
