package benchmark.hibernate.utils;

import java.io.IOException;
import java.io.InputStream;

import org.morm.exception.SormException;

import java.util.Locale;
import java.util.Properties;

public class PropertiesLoader {

	public static final String PROPERTIES_EXTENSION = ".properties";
	public static final String LOCALE_SEPARATOR = "_";

	public static Properties load(final String propertiesBaseName) {
		return load2(propertiesBaseName + PROPERTIES_EXTENSION);
	}

	public static Properties load(final String propertiesBaseName, final Locale locale) {
		try {
			return load2(propertiesBaseName + LOCALE_SEPARATOR + locale.toString() + PROPERTIES_EXTENSION);
		} catch (final SormException e) {
			try {
				return load2(propertiesBaseName + PROPERTIES_EXTENSION);
			} catch (final Exception e2) {
				throw new SormException("not found for locale=" + locale.toString()
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
			throw new SormException("could not open configuration file: " + propertiesFileName);
		}
		try {
			return load(is);
		} catch (final IOException e) {
			throw new SormException("could not read configuration from: " + propertiesFileName, e);
		}
	}

}
