package org.frijoles.binder;

import java.util.Map;

public class VException extends RuntimeException {

	private static final long serialVersionUID = 2671728512326429140L;

	protected final Map<String, State> errorStatesMap;
	protected final Map<String, String> errorsMap;

	public VException(final Map<String, State> errorStatesMap, final Map<String, String> errorsMap) {
		super();
		this.errorStatesMap = errorStatesMap;
		this.errorsMap = errorsMap;
	}

	public Map<String, State> getErrorStatesMap() {
		return errorStatesMap;
	}

	public Map<String, String> getErrorsMap() {
		return errorsMap;
	}

}