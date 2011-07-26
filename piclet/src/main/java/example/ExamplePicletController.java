package example;

import org.homs.piclet.Piclet;
import org.homs.piclet.PicletController;

import java.util.Map;

public class ExamplePicletController extends PicletController {

	private static final long serialVersionUID = 7374423578676646960L;

	@Override
	protected void registerPiclets(final Map<String, Piclet> picletsMap) {
		picletsMap.put("/bar.piclet", new ExamplePiclet());
	}

}
