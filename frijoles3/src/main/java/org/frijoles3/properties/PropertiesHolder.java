package org.frijoles3.properties;

import java.io.IOException;
import java.io.InputStream;

import java.util.Locale;
import java.util.Properties;

public class PropertiesHolder {

	protected final Properties props;

	public PropertiesHolder(final Properties props) {
		this.props = props;
	}

	public PropertiesHolder(final InputStream is) throws IOException {
		this.props = PropertiesLoader.load(is);
	}

	public PropertiesHolder(final String propertiesBaseName) {
		this.props = PropertiesLoader.load(propertiesBaseName);
	}

	public PropertiesHolder(final String propertiesBaseName, final Locale locale) {
		this.props = PropertiesLoader.load(propertiesBaseName, locale);
	}

}
