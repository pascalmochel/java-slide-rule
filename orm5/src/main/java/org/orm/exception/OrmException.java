package org.orm.exception;

public class OrmException extends RuntimeException {

	private static final long serialVersionUID = 1835360421671345458L;

	@Deprecated
	public OrmException() {
		super();
	}

	@Deprecated
	public OrmException(final Throwable e) {
		super(e);
	}

	public OrmException(final String msg) {
		super(msg);
	}

	public OrmException(final String msg, final Throwable e) {
		super(msg, e);
	}

	/**
	 * shows a brief resume of nested exceptions
	 * 
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return getBriefListTrace() + super.toString();
	}

	public String stackTraceToString() {
		return ThrowableRenderer.stackTraceToString(this);
	}

	public String getBriefListTrace() {
		return ThrowableRenderer.getBriefListTrace(this);
	}

}
