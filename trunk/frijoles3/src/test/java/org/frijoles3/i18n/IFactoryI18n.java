package org.frijoles3.i18n;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Prototype;
import org.springframework.context.MessageSource;

import java.util.Locale;

public interface IFactoryI18n {

	@Scope
	Locale getDefaultLocale(IFactoryI18n self);

	@Scope
	MessageSource getMessageSource(IFactoryI18n self);

	@Scope(Prototype.class)
	String getMessage(IFactoryI18n self, String key, Object... parameters);

	@Scope(Prototype.class)
	String getMessage(IFactoryI18n self, String key, Locale locale, Object... parameters);
}