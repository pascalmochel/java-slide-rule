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

	protected final Map<String, IPiclet> picletsMap;

	public PicletController() {
		super();
		this.picletsMap = new HashMap<String, IPiclet>();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		registerPiclets(this.picletsMap);
	}

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		obtainPiclet(request).doDownload(request, response);
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		obtainPiclet(request).doDownload(request, response);
	}

	protected IPiclet obtainPiclet(final HttpServletRequest request) {
		final String picletAlias = request.getServletPath();
		final IPiclet piclet = picletsMap.get(picletAlias);
		if (piclet == null) {
			throw new RuntimeException("no piclet registered in " + getClass().getSimpleName()
					+ " aliased as " + picletAlias);
		}
		return piclet;
	}

	protected abstract void registerPiclets(Map<String, IPiclet> picletsMap);

}
