package org.frijoles3;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class VTest {

	@Test
	public void testname() throws Exception {

		assertFail("mhc", null, "{age=age is required}");
		assertFail("mhc", "", "{age=age is required}");
		assertFail("mhc", "A", "{age=age must be numeric}");
		assertFail("mhc", "-5", "{age=age must be in range}");
		assertFail("mhc", "800", "{age=age must be in range}");
		assertSuccess("mhc", "29");

		assertFail("mh", "800", "{age=age must be in range, name=name must be of 3..6 chars}");
	}

	private void validate(final String name, final String age) {
		new V()
		/**/.beginValidation("name", name)
		/**/.setMessage("name is required")
		/**/.notEmpty()
		/**/.setMessage("name must be of 3..6 chars")
		/**/.lengthInRange(3, 6)
		/**/
		/**/.beginValidation("age", age)
		/**/.setMessage("age is required")
		/**/.notEmpty()
		/**/.setMessage("age must be numeric")
		/**/.asInteger()
		/**/.setMessage("age must be in range")
		/**/.numericInRange(0, 150)
		/**/
		/**/.validateAndThrow()
		/**/;
	}

	private void assertSuccess(final String name, final String age) {
		validate(name, age);
	}

	private void assertFail(final String name, final String age, final String messages) {
		try {
			assertSuccess(name, age);
			fail();
		} catch (final ValidationException e) {
			assertEquals(messages, e.getValidationMap().toString());
		}
	}

}

class VState {

	protected final String propertyName;
	protected Object value;

	protected boolean failed;
	protected String errorMessage;
	protected Throwable errorCause;

	protected String nextErrorMessage;

	public VState(final String propertyName, final Object value) {
		super();
		this.propertyName = propertyName;
		this.value = value;
		this.failed = false;
	}

	public boolean isNotFailed() {
		return !failed;
	}

	public void setFailed() {
		this.failed = true;
		this.errorMessage = this.nextErrorMessage;
	}

	public void setFailed(final Throwable errorCause) {
		this.failed = true;
		this.errorMessage = this.nextErrorMessage;
		this.errorCause = errorCause;
	}

	// public String getPropertyName() {
	// return propertyName;
	// }

	public Object getValue() {
		return value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	// public void setErrorMessage(final String errorMessage) {
	// this.errorMessage = errorMessage;
	// }

	public void setNextErrorMessage(final String nextErrorMessage) {
		this.nextErrorMessage = nextErrorMessage;
	}

	@Override
	public String toString() {
		return errorMessage == null ? errorCause.toString() : errorMessage;
	}

}

class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 2671728512326429140L;

	protected final Map<String, VState> validationMap;

	public ValidationException(final Map<String, VState> validationMap) {
		super();
		this.validationMap = validationMap;
	}

	public Map<String, VState> getValidationMap() {
		return validationMap;
	}

}

class V {

	protected Map<String, VState> errorsMap;
	protected String currProperty;
	protected VState currState;

	public V() {
		super();
		this.errorsMap = new HashMap<String, VState>();
	}

	public V beginValidation(final String propertyName, final Object value) {
		this.currProperty = propertyName;
		this.currState = new VState(propertyName, value);
		return this;
	}

	public V beginValidation(final String propertyName, final Object value, final String errMessage) {
		this.currProperty = propertyName;
		this.currState = new VState(propertyName, value);
		currState.nextErrorMessage = errMessage;
		return this;
	}

	public boolean validate() {
		return errorsMap.isEmpty();
	}

	public void validateAndThrow() {
		if (!validate()) {
			throw new ValidationException(errorsMap);
		}
	}

	public V setMessage(final String errMessage) {
		currState.setNextErrorMessage(errMessage);
		return this;
	}

	/*
	 * validate empty fields ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	public V notNull() {
		if (currState.isNotFailed()) {
			if (currState.getValue() == null) {
				currState.setFailed();
				errorsMap.put(currProperty, currState);
			}
		}
		return this;
	}

	public V notEmpty() {
		if (currState.isNotFailed()) {
			if (currState.getValue() == null || currState.getValue().toString().length() == 0) {
				currState.setFailed();
				errorsMap.put(currProperty, currState);
			}
		}
		return this;
	}

	/*
	 * field conversion ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	public V asInteger() {
		if (currState.isNotFailed()) {
			try {
				currState.setValue(Integer.valueOf(currState.getValue().toString()));
			} catch (final Exception e) {
				currState.setFailed(e);
				errorsMap.put(currProperty, currState);
			}
		}
		return this;
	}

	/*
	 * validate numerics ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	public V numericInRange(final double min, final double max) {
		if (currState.isNotFailed()) {
			final double doubleValue = ((Number) currState.getValue()).doubleValue();
			if (doubleValue < min || doubleValue > max) {
				currState.setFailed();
				errorsMap.put(currProperty, currState);
			}
		}
		return this;
	}

	/*
	 * validate literals ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	public V lengthInRange(final double min, final double max) {
		if (currState.isNotFailed()) {
			final int len = currState.getValue().toString().length();
			if (len < min || len > max) {
				currState.setFailed();
				errorsMap.put(currProperty, currState);
			}
		}
		return this;
	}

}
