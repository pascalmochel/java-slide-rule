package org.frijoles3.properties;

import java.io.IOException;
import java.io.InputStream;

import org.frijoles3.exception.FrijolesException;

import java.util.Locale;
import java.util.Properties;

public class PropertiesLoader {

	public static Properties load(final String propertiesBaseName) {
		return load2(propertiesBaseName + ".properties");
	}

	public static Properties load(final String propertiesBaseName, final Locale locale) {
		try {
			return load2(propertiesBaseName + "_" + locale.toString() + ".properties");
		} catch (final FrijolesException e) {
			try {
				return load2(propertiesBaseName + ".properties");
			} catch (final Exception e2) {
				throw new FrijolesException("not found for locale=" + locale.toString()
						+ " nor for default locale", e2);
			}
		}
	}

	public static Properties load(final InputStream is) throws IOException {

		final Properties props = new Properties();
		props.load(is);
		return props;
	}

	protected static Properties load2(final String propertiesFileName) {

		final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(
				propertiesFileName);
		if (is == null) {
			throw new FrijolesException("could not open configuration file: " + propertiesFileName);
		}
		try {
			return load(is);
		} catch (final IOException e) {
			throw new FrijolesException("could not read configuration from: " + propertiesFileName);
		}
	}

}
