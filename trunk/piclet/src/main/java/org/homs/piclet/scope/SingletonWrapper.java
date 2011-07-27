package org.homs.piclet.scope;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;

import org.homs.piclet.plotter.IPlotter;

public class SingletonWrapper implements IScopeWrapper {

	protected final IPlotter plotter;

	protected final Object mutex = new Object();
	protected BufferedImage image;
	protected volatile boolean initializated = false;

	public SingletonWrapper(final IPlotter plotter) {
		super();
		this.plotter = plotter;
	}

	@Override
	public BufferedImage getImage(final HttpServletRequest request) {

		if (!initializated) {
			synchronized (mutex) {
				if (!initializated) {
					this.image = plotter.getImage(request);
					initializated = true;
				}
			}
		}
		return this.image;
	}

}
