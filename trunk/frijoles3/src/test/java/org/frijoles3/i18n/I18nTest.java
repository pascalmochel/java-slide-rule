package org.frijoles3.i18n;

import org.frijoles3.FactoryBuilder;
import org.junit.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

import static org.junit.Assert.*;

public class I18nTest {

	@Test
	public void testname() throws Exception {
		final ResourceBundleMessageSource r = new ResourceBundleMessageSource();
		r.setBasename("i18n-testing");
		Locale.setDefault(new Locale("es"));

		String m = r.getMessage("salute", new Object[] { "Frijoles" }, null);
		assertEquals("Hola mundo! mi nombre es Frijoles", m);

		m = r.getMessage("salute", new Object[] { "Frijoles" }, Locale.ENGLISH);
		assertEquals("Hello world! my name is Frijoles", m);
	}

	@Test
	public void testname2() throws Exception {

		final IFactoryI18n f = FactoryBuilder.build(FactoryI18n.class);

		String m = f.getMessage(null, "salute", "Frijoles");
		assertEquals("Hola mundo! mi nombre es Frijoles", m);

		m = f.getMessage(null, "salute", Locale.ENGLISH, "Frijoles");
		assertEquals("Hello world! my name is Frijoles", m);
	}

}
