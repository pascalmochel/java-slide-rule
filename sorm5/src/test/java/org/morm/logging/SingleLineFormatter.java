package org.morm.logging;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Print a brief summary of the LogRecord in a human readable format. The
 * summary will be of 1 line.
 * 
 * <pre>
 * [INFO] 17:42:024 LogWrapperTest The quick brown fox jumped over the lazy dog.
 * </pre>
 * 
 * @author mhoms
 */
public class SingleLineFormatter extends Formatter {

	protected final String timeFormat;
	protected final StringBuilder strb;

	public SingleLineFormatter() {
		this("HH:mm:SSS");
	}

	public SingleLineFormatter(final String timeFormat) {
		this.timeFormat = timeFormat;
		this.strb = new StringBuilder();
	}

	@Override
	public String format(final LogRecord rec) {

		final String[] packageParts = rec.getLoggerName().split("\\.");
		final String classSimpleName = packageParts[packageParts.length - 1];

		strb.setLength(0);
		return strb //
		.append('[') //
		.append(rec.getLevel().getName()) //
		.append("] ") //
		.append(calcDate(rec.getMillis())) //
		.append(' ') //
		.append(classSimpleName) //
		.append(' ') //
		.append(formatMessage(rec)) //
		.append('\n') //
		.toString();
	}

	protected String calcDate(final long millisecs) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat);
		final Date resultdate = new Date(millisecs);
		return dateFormat.format(resultdate);
	}

}
