package org.frijoles4.aliasp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AliasDecameler implements IAliasProcessor {

	public static final String SEPARATOR = "-";
	protected static final String REPLACEMENT = SEPARATOR + "$1";
	// capturar qualsevol majúscula
	protected static final Pattern DECAMELIZE_EXP = Pattern.compile("([A-Z])");

	public String processAlias(String s) {

		final Matcher m = DECAMELIZE_EXP.matcher(s);
		String r = m.replaceAll(REPLACEMENT).toLowerCase();
		if (r.startsWith("get-")) {
			r = r.substring(4);
		}
		return r;
	}

}
