package org.frijoles4.test.properties;

import java.util.Properties;

import javax.servlet.ServletRequest;

import org.frijoles4.FrijolesContext;
import org.frijoles4.anno.Scope;
import org.frijoles4.properties.PropertiesLoader;
import org.frijoles4.scope.impl.Session;

public class PropsFactory {

	protected static final String PROPERTIES_TEST = "frijoles-test";
	protected Properties props;

	@Scope
	public Properties getProperties(final FrijolesContext ctx) {
		return PropertiesLoader.load(PROPERTIES_TEST);
	}

	@Scope
	public String getSalute(final FrijolesContext ctx) {
		return ctx.getBean(Properties.class, "properties").getProperty("salute");
	}

	/**
	 * XXX aix� �s un exemple puta mare per a web
	 */
	@Scope(Session.class)
	public Properties getLocalizedProperties(FrijolesContext ctx, final ServletRequest request) {
		return PropertiesLoader.load(PROPERTIES_TEST, request.getLocale());
	}

}
