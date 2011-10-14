package org.orm.logging;

import org.mb.exception.ThrowableRenderer;

import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogWrapper {

	private static final String ENTERING_SYM = "-> ";
	private static final String EXITING_SYM = "<- ";

	protected final java.util.logging.Logger logger;

	protected LogWrapper(final java.util.logging.Logger logger) {
		this.logger = logger;
	}

	// simple messaging methods

	public void severe(final Object msg) {
		log(null, Level.SEVERE, msg);
	}

	public void severe(final Throwable e) {
		log(e, Level.SEVERE, "");
	}

	public void severe(final Throwable e, final Object msg) {
		log(e, Level.SEVERE, msg);
	}

	public void warning(final Object msg) {
		log(null, Level.WARNING, msg);
	}

	public void info(final Object msg) {
		log(null, Level.INFO, msg);
	}

	public void config(final Object msg) {
		log(null, Level.CONFIG, msg);
	}

	public void fine(final Object msg) {
		log(null, Level.FINE, msg);
	}

	public void finer(final Object msg) {
		log(null, Level.FINER, msg);
	}

	public void finest(final Object msg) {
		log(null, Level.FINEST, msg);
	}

	// --------------------------------------------
	// processed messages methods

	public void severe(final String msg, final Object... params) {
		log(null, Level.SEVERE, msg, params);
	}

	public void severe(final Throwable e, final String msg, final Object... params) {
		log(e, Level.SEVERE, msg, params);
	}

	public void warning(final String msg, final Object... params) {
		log(null, Level.WARNING, msg, params);
	}

	public void info(final String msg, final Object... params) {
		log(null, Level.INFO, msg, params);
	}

	public void config(final String msg, final Object... params) {
		log(null, Level.CONFIG, msg, params);
	}

	public void fine(final String msg, final Object... params) {
		log(null, Level.FINE, msg, params);
	}

	public void finer(final String msg, final Object... params) {
		log(null, Level.FINER, msg, params);
	}

	public void finest(final String msg, final Object... params) {
		log(null, Level.FINEST, msg, params);
	}

	// --------------------------------------------

	public void entering() {
		logger.finer(ENTERING_SYM + LogFactory.getCallerMethodName());
	}

	public void exiting() {
		logger.finer(EXITING_SYM + LogFactory.getCallerMethodName());
	}

	public void entering(final Level level) {
		log(null, level, ENTERING_SYM + LogFactory.getCallerMethodName());
	}

	public void exiting(final Level level) {
		log(null, level, EXITING_SYM + LogFactory.getCallerMethodName());
	}

	// ------------------------------------------------------------------------------

	protected String toString(final Object o) {
		if (o == null) {
			return "null";
		} else if (o.getClass().isArray()) {
			return Arrays.toString((Object[]) o);
		}
		return o.toString();
	}

	public void log(final Throwable t, final Level level, final Object message) {

		if (t == null) {
			logger.log(level, toString(message));
		} else {
			logger.log(level, toString(message) + ThrowableRenderer.stackTraceToString(t));
		}
	}

	public void log(final Throwable t, final Level level, final String message, final Object[] params) {

		if (t == null) {
			logger.log(level, message, params);
		} else {
			logger.log(level, message + ThrowableRenderer.stackTraceToString(t), params);
		}
	}

	// ------------------------------------------------------------------------------

	// public void addHandler(final Handler handler) {
	// logger.addHandler(handler);
	// }
	//
	// public Handler[] getHandlers() {
	// return logger.getHandlers();
	// }
	//
	// public Filter getFilter() {
	// return logger.getFilter();
	// }
	//
	// public void setFilter(final Filter filter) {
	// logger.setFilter(filter);
	// }
	//
	// public Level getLevel() {
	// return logger.getLevel();
	// }
	//
	// public void setLevel(final Level level) {
	// logger.setLevel(level);
	// }
	//
	// public String getName() {
	// return logger.getName();
	// }
	//
	// public ResourceBundle getResourceBundle() {
	// return logger.getResourceBundle();
	// }
	//
	// public String getResourceBundleName() {
	// return logger.getResourceBundleName();
	// }

	public void addHandler(final Handler handler) throws SecurityException {
		logger.addHandler(handler);
	}

	public Filter getFilter() {
		return logger.getFilter();
	}

	public Handler[] getHandlers() {
		return logger.getHandlers();
	}

	public Level getLevel() {
		return logger.getLevel();
	}

	public String getName() {
		return logger.getName();
	}

	public Logger getParent() {
		return logger.getParent();
	}

	public ResourceBundle getResourceBundle() {
		return logger.getResourceBundle();
	}

	public String getResourceBundleName() {
		return logger.getResourceBundleName();
	}

	public boolean getUseParentHandlers() {
		return logger.getUseParentHandlers();
	}

	public void removeHandler(final Handler handler) throws SecurityException {
		logger.removeHandler(handler);
	}

	public void setFilter(final Filter newFilter) throws SecurityException {
		logger.setFilter(newFilter);
	}

	public void setLevel(final Level newLevel) throws SecurityException {
		logger.setLevel(newLevel);
	}

	public void setParent(final Logger parent) {
		logger.setParent(parent);
	}

	public void setUseParentHandlers(final boolean useParentHandlers) {
		logger.setUseParentHandlers(useParentHandlers);
	}

	// --------------------------------------

	// @Override
	// public boolean equals(final Object obj) {
	// return obj instanceof LogWrapper && logger.equals(((LogWrapper)
	// obj).logger);
	// }
	//
	// @Override
	// public int hashCode() {
	// return logger.hashCode();
	// }

	/**
	 * accessor to the wrapped instance of {@link Logger}
	 */
	public java.util.logging.Logger getWrappedLogger() {
		return logger;
	}

}
