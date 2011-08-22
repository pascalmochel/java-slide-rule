package org.frijoles4.exception;

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

	// protected static String getSimpleHtmlStackTrace(final Throwable
	// throwable) {
	//
	// final StringBuilder strb = new StringBuilder(200) //
	// .append("<br />") //
	// .append("<b>" + throwable.toString() + "</b>");
	//
	// for (final StackTraceElement s : throwable.getStackTrace()) {
	// strb.append(s).append("<br />");
	// }
	//
	// return strb.toString();
	// }
	//
	// public static String getHtmlStackTrace(final Throwable e) {
	//
	// final StringBuilder strb = new StringBuilder(200);
	//
	// Throwable throwable = e;
	// while (throwable != null) {
	//
	// strb.append(getSimpleHtmlStackTrace(throwable));
	// throwable = throwable.getCause();
	// }
	//
	// return strb.toString();
	// }

}
