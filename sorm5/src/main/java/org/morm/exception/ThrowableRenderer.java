package org.morm.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ThrowableRenderer {

	protected ThrowableRenderer() {
	}

	public static String stackTraceToString(final Throwable t) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream p = new PrintStream(baos);
		t.printStackTrace(p);
		return baos.toString();
	}

	public static String getBriefListTrace(final Throwable t) {

		final StringBuilder strb = new StringBuilder(200);

		Throwable throwable = t;
		while (throwable != null) {
			if (throwable != t) {
				strb.append(";\n[cause is:] ");
			}
			strb.append(throwable.getClass().getSimpleName()) //
					.append(": ") //
					.append(throwable.getMessage());

			throwable = throwable.getCause();
		}
		strb.append('\n');
		return strb.toString();
	}

}
