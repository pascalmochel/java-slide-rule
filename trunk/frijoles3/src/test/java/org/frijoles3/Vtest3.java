package org.frijoles3;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * v.or(v.isNull(), v.isNumeric()) v = for("age", age); or(isNull(v),
 * isNumeric(v))
 */
public class Vtest3 {

	@Test
	public void testname() throws Exception {

		final Errors e = new Errors();

		final String name = State.begin(e, "name", "mhc")
		/**/.message("must be not null")
		/**/.isNotNull()
		/**/.message("must have 3..6 length")
		/**/.lengthInRange(3, 6)
		/**/.getValue()
		/**/;

		final Integer age = State.begin(e, "age", "5")
		/**/.message("must be not null")
		/**/.isNotNull()
		/**/.message("must be numeric")
		/**/.isNumeric()
		/**/.asInteger()
		/**/.message("must values 0..130")
		/**/.numericInRange(0, 130)
		/**/.getValue()
		/**/;

		System.out.println(name + "," + age);
		try {
			e.validateAndThrow();
		} catch (final VException e2) {
			System.out.println(e2.getErrorsMap());
		}

	}
}

class VException extends RuntimeException {

	private static final long serialVersionUID = 2671728512326429140L;

	protected final Map<String, State> errorStatesMap;
	protected final Map<String, String> errorsMap;

	public VException(final Map<String, State> errorStatesMap) {
		super();
		this.errorStatesMap = errorStatesMap;
		this.errorsMap = new HashMap<String, String>();

		for (final Entry<String, State> e : errorStatesMap.entrySet()) {
			errorsMap.put(e.getKey(), e.getValue().getMessage());
		}
	}

	public Map<String, State> getErrorStatesMap() {
		return errorStatesMap;
	}

	public Map<String, String> getErrorsMap() {
		return errorsMap;
	}

}

class Errors {

	protected final Map<String, State> errorsMap;

	public Errors() {
		errorsMap = new HashMap<String, State>();
	}

	public void registerError(final String propname, final State failedState) {
		errorsMap.put(propname, failedState);
	}

	public boolean validate() {
		return errorsMap.isEmpty();
	}

	public void validateAndThrow() {
		if (!validate()) {
			throw new VException(errorsMap);
		}
	}

	public Map<String, State> getErrorsMap() {
		return errorsMap;
	}

}

class State {

	protected final Errors errors;

	protected final boolean isFailed;
	protected final String message;

	protected final String propName;
	protected final Object value;

	protected State(final Errors errors, final String propName, final boolean isFailed, final String message,
			final Object value) {
		super();
		this.errors = errors;
		this.propName = propName;
		this.isFailed = isFailed;
		this.message = message;
		this.value = value;
		System.out.println(this);
	}

	public static State begin(final Errors errors, final String propName, final Object value) {
		return new State(errors, propName, false, null, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue() {
		if (isFailed) {
			errors.registerError(propName, this);
			return null;
		} else {
			return (T) value;
		}
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "State [isFailed=" + isFailed + ", message=" + message + ", value=" + value + "]";
	}

	private State fail() {
		return new State(errors, propName, true, message, value);
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public State message(final String msg) {
		if (isFailed) {
			return this;
		}
		return new State(errors, propName, false, msg, value);
	}

	public State isNull() {
		if (isFailed) {
			return this;
		}
		if (value == null) {
			return this;
		} else {
			return fail();
		}
	}

	public State isNotNull() {
		if (isFailed) {
			return this;
		}
		if (value != null) {
			return this;
		} else {
			return fail();
		}
	}

	public State isNumeric() {
		if (isFailed) {
			return this;
		}
		boolean cond;
		try {
			Double.valueOf(value.toString());
			cond = true;
		} catch (final Exception e) {
			cond = false;
		}
		if (cond) {
			return this;
		} else {
			return fail();
		}
	}

	public State asInteger() {
		if (isFailed || value == null) {
			return this;
		}
		try {
			final Integer v = Integer.valueOf(value.toString());
			return new State(errors, propName, isFailed, message, v);
		} catch (final Exception e) {
			return fail();
		}
	}

	public State lengthInRange(final int min, final int max) {
		if (isFailed || value == null) {
			return this;
		}
		final int v = value.toString().length();
		if (min <= v && v <= max) {
			return this;
		} else {
			return fail();
		}
	}

	public State numericInRange(final double min, final double max) {
		if (isFailed || value == null) {
			return this;
		}
		if (!(value instanceof Number)) {
			return fail();
		}

		final double v = ((Number) value).doubleValue();
		if (min <= v && v <= max) {
			return this;
		} else {
			return fail();
		}
	}

}
