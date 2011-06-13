package org.frijoles3.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

public class FactoryI18n implements IFactoryI18n {

	@Override
	public Locale getDefaultLocale(final IFactoryI18n self) {
		final Locale r = new Locale("es");
		Locale.setDefault(r);
		return r;
	}

	@Override
	public MessageSource getMessageSource(final IFactoryI18n self) {
		final ResourceBundleMessageSource r = new ResourceBundleMessageSource();
		r.setBasename("i18n-testing");
		return r;
	}

	@Override
	public String getMessage(final IFactoryI18n self, final String key, final Object... parameters) {
		return self.getMessageSource(null).getMessage(key, parameters, self.getDefaultLocale(null));
	}

	@Override
	public String getMessage(final IFactoryI18n self, final String key, final Locale locale,
			final Object... parameters) {
		return self.getMessageSource(null).getMessage(key, parameters, locale);
	}

}
