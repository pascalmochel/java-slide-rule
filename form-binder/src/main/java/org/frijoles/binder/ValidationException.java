package org.frijoles.binder;

import java.util.Map;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 2671728512326429140L;

	protected final ValidationErrors validationErrors;

	public ValidationException(final ValidationErrors validationErrors) {
		super();
		this.validationErrors = validationErrors;
	}

	public Map<String, State> getErrorStatesMap() {
		return validationErrors.getErrorStatesMap();
	}

	public Map<String, String> getErrorsMap() {
		return validationErrors.getErrorsMap();
	}

}