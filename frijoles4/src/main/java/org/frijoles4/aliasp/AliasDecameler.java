package org.frijoles4.aliasp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AliasDecameler implements IAliasProcessor {

	public static final String SEPARATOR = "-";
	// capturar qualsevol majúscula
	protected static final Pattern DECAMELIZE_EXP = Pattern.compile("([A-Z])");

	public String processAlias(String s) {

		final Matcher m = DECAMELIZE_EXP.matcher(s);
		String r = m.replaceAll(SEPARATOR + "$1").toLowerCase();
		if (r.startsWith("get-")) {
			r = r.substring(4);
		}
		return r;
	}

	// @Test
	// public void testname() {
	// AliasDecameler a = new AliasDecameler();
	// assertEquals("id-dog", a.processAlias("idDog"));
	// assertEquals("id-a-b-c-dog", a.processAlias("idABCDog"));
	// assertEquals("id-dog", a.processAlias("getIdDog"));
	// }

}
