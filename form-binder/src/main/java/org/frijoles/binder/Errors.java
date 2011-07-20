package org.frijoles.binder;

import java.util.HashMap;
import java.util.Map;

public class Errors {

	protected final Map<String, State> errorStatesMap;
	protected final Map<String, String> errorsMap;

	public Errors() {
		errorStatesMap = new HashMap<String, State>();
		errorsMap = new HashMap<String, String>();
	}

	public void registerError(final String propname, final State failedState) {
		errorStatesMap.put(propname, failedState);
		errorsMap.put(propname, failedState.getMessage());
	}

	public boolean validate() {
		return errorStatesMap.isEmpty();
	}

	public void validateAndThrow() {
		if (!validate()) {
			throw new VException(errorStatesMap, errorsMap);
		}
	}

	public Map<String, State> getErrorStatesMap() {
		return errorStatesMap;
	}

	public Map<String, String> getErrorsMap() {
		return errorsMap;
	}

	public void clear() {
		errorsMap.clear();
		errorStatesMap.clear();
	}

}
