package org.frijoles3.properties;

import javax.servlet.ServletRequest;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Session;

import java.util.Properties;

public class F implements IF {

	protected Properties props;

	@Scope
	public Properties getProperties(final IF self) {
		return PropertiesLoader.load("frijoles-test");
	}

	@Scope
	public String getSalute(final IF self) {
		return self.getProperties(null).getProperty("salute");
	}

	/**
	 * XXX això és un exemple puta mare per a web
	 */
	@Scope(Session.class)
	public Properties getLocalizedProperties(final ServletRequest request) {
		return PropertiesLoader.load("frijoles-test", request.getLocale());
	}

}
