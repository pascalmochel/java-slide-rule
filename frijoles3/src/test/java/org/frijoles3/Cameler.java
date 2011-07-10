package org.frijoles3;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class Cameler {

	/**
	 * capturar qualsevol majúscula
	 */
	protected static final Pattern DECAMELIZE_EXP = Pattern.compile("([A-Z])");

	/**
	 * un "_" i capturant el seguent caràcter
	 */
	protected static final Pattern ENCAMELIZE_EXP = Pattern.compile("_+(.)");

	public static String decamelize(final String s) {
		final Matcher m = DECAMELIZE_EXP.matcher(s);
		return m.replaceAll("_$1").toUpperCase();
	}

	public static String encamelize(String s) {
		s = s.toLowerCase();
		final Matcher m = ENCAMELIZE_EXP.matcher(s);

		final StringBuilder sb = new StringBuilder();
		int last = 0;
		while (m.find()) {
			sb.append(s.substring(last, m.start()));
			sb.append(m.group(1).toUpperCase());
			last = m.end();
		}
		sb.append(s.substring(last));
		return sb.toString();
	}

	@Test
	public void testname() {
		assertEquals("ID_DOG", decamelize("idDog"));
		assertEquals("idDog", encamelize("ID_DOG"));

		assertEquals("ID_A_B_C_DOG", decamelize("idABCDog"));
		assertEquals("idABCDog", encamelize("ID_A_B_C_DOG"));
		assertEquals("idABCDog", encamelize("ID_A__B___C_DOG"));
	}

}
