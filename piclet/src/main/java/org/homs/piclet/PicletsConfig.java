package org.homs.piclet;

import org.homs.piclet.plotter.IPlotter;
import org.homs.piclet.scope.IScopeWrapper;
import org.homs.piclet.scope.RequestWrapper;
import org.homs.piclet.scope.SessionWrapper;
import org.homs.piclet.scope.SingletonWrapper;

import java.util.HashMap;
import java.util.Map;

public class PicletsConfig {

	private static final String DEFAULT_EXTENSION = "png";

	protected final Map<String, IPiclet> picletsMap;

	public PicletsConfig() {
		super();
		this.picletsMap = new HashMap<String, IPiclet>();
	}

	public PicletsConfig def(final String url, final String extension, final IScopeWrapper scopeWrapperImpl) {
		this.picletsMap.put(url, new Piclet(extension, scopeWrapperImpl));
		return this;
	}

	public PicletsConfig defAsRequest(final String url, final String extension, final IPlotter plotter) {
		this.picletsMap.put(url, new Piclet(extension, new RequestWrapper(plotter)));
		return this;
	}

	public PicletsConfig defAsRequest(final String url, final IPlotter plotter) {
		this.picletsMap.put(url, new Piclet(DEFAULT_EXTENSION, new RequestWrapper(plotter)));
		return this;
	}

	public PicletsConfig defAsSession(final String url, final String extension, final IPlotter plotter) {
		this.picletsMap.put(url, new Piclet(extension, new SessionWrapper(plotter)));
		return this;
	}

	public PicletsConfig defAsSession(final String url, final IPlotter plotter) {
		this.picletsMap.put(url, new Piclet(DEFAULT_EXTENSION, new SessionWrapper(plotter)));
		return this;
	}

	public PicletsConfig defAsSingleton(final String url, final String extension, final IPlotter plotter) {
		this.picletsMap.put(url, new Piclet(extension, new SingletonWrapper(plotter)));
		return this;
	}

	public PicletsConfig defAsSingleton(final String url, final IPlotter plotter) {
		this.picletsMap.put(url, new Piclet(DEFAULT_EXTENSION, new SingletonWrapper(plotter)));
		return this;
	}

	public Map<String, IPiclet> getPicletsMap() {
		return picletsMap;
	}

}
