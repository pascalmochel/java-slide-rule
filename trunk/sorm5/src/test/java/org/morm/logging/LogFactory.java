package org.morm.logging;

import javax.security.auth.login.Configuration;

import java.util.logging.Logger;

/**
 * Factory to obtain easily the correctly named {@link Logger} and
 * {@link LogWrapper} instances.
 * <p>
 * Includes a set of factory methods that automatically assigns the properly
 * logger name, but its use is discouraged except for a quick prototyping.
 * 
 * @author mhoms
 */
public class LogFactory {

	private static final String ROOT_LOGGERNAME = "";

	protected LogFactory() {
		//
	}

	/**
	 * @return the root {@link Logger} instance
	 * @see Configuration#ROOTLOGGER_NAME
	 */
	public static Logger getRootLogger() {
		return java.util.logging.Logger.getLogger(ROOT_LOGGERNAME);
	}

	/**
	 * @return an auto-named logger
	 */
	public static Logger getLogger() {
		return java.util.logging.Logger.getLogger(getCallerClassName());
	}

	// public static Logger getLogger(final String name) {
	// return java.util.logging.Logger.getLogger(name);
	// }
	//
	// public static Logger getLogger(final String name, final String
	// resourceBundleName) {
	// return Logger.getLogger(name, resourceBundleName);
	// }

	public static Logger getLogger(final Class<?> claz) {
		return Logger.getLogger(claz.getName());
	}

	public static Logger getLogger(final Class<?> claz, final String resourceBundleName) {
		return Logger.getLogger(claz.getName(), resourceBundleName);
	}

	// --------------------------------------------

	// /**
	// * @return the root {@link LogWrapper} instance
	// * @see Configuration#ROOTLOGGER_NAME
	// */
	// public static LogWrapper getRootWrappedLog() {
	// return new
	// LogWrapper(java.util.logging.Logger.getLogger(ROOT_LOGGERNAME));
	// }

	/**
	 * @return an auto-named logger
	 */
	public static LogWrapper getWrappedLog() {
		return new LogWrapper(java.util.logging.Logger.getLogger(getCallerClassName()));
	}

	// public static LogWrapper getWrappedLog(final String name) {
	// return new LogWrapper(java.util.logging.Logger.getLogger(name));
	// }
	//
	// public static LogWrapper getWrappedLog(final String name, final String
	// resourceBundleName) {
	// return new LogWrapper(java.util.logging.Logger.getLogger(name,
	// resourceBundleName));
	// }

	public static LogWrapper getWrappedLog(final Class<?> claz) {
		return new LogWrapper(java.util.logging.Logger.getLogger(claz.getName()));
	}

	public static LogWrapper getWrappedLog(final Class<?> claz, final String resourceBundleName) {
		return new LogWrapper(java.util.logging.Logger.getLogger(claz.getName(), resourceBundleName));
	}

	public static String getCallerClassName() {
		return new Exception().getStackTrace()[2].getClassName();
	}

	public static String getCallerMethodName() {
		return new Exception().getStackTrace()[2].getMethodName();
	}
}
