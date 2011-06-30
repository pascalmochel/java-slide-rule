package org.frijoles3.properties;

import java.io.IOException;
import java.io.InputStream;

import java.util.Locale;
import java.util.Properties;

public class PropertiesHolder extends Properties {

	private static final long serialVersionUID = -8086127675176348896L;

	public PropertiesHolder(final Properties props) {
		super(props);
	}

	public PropertiesHolder(final InputStream is) throws IOException {
		super(PropertiesLoader.load(is));
	}

	public PropertiesHolder(final String propertiesBaseName) {
		super(PropertiesLoader.load(propertiesBaseName));
	}

	public PropertiesHolder(final String propertiesBaseName, final Locale locale) {
		super(PropertiesLoader.load(propertiesBaseName, locale));
	}

}