package org.homs.piclet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class PicletController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected final Map<String, Piclet> picletsMap;

	public PicletController() {
		super();
		this.picletsMap = new HashMap<String, Piclet>();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		registerPiclets(this.picletsMap);
	}

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final Piclet piclet = picletsMap.get(request.getServletPath());
		piclet.doGet(request, response);
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final Piclet piclet = picletsMap.get(request.getServletPath());
		piclet.doGet(request, response);
	}

	protected abstract void registerPiclets(Map<String, Piclet> picletsMap);

}
