package org.frijoles3;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class Vtest3 {
	@Test
	public void testnameDuh() throws Exception {
		final Errors e = new Errors();

		final Integer age = State.begin(e, "age", 28).getValue();
		assertEquals(Integer.valueOf(28), age);
		e.validateAndThrow();
	}

	@Test
	public void testnameBindOk() throws Exception {
		final Errors e = new Errors();

		final Integer age = State.begin(e, "age", "28").asInteger().getValue();
		assertEquals(Integer.valueOf(28), age);
		e.validateAndThrow();
	}

	@Test
	public void testnameBindFails() throws Exception {
		final Errors e = new Errors();

		final Integer age = State.begin(e, "age", "jou").message("err").asInteger().getValue();
		assertNull(age);
		try {
			e.validateAndThrow();
		} catch (final VException e2) {
			assertEquals("{age=err}", e2.getErrorsMap().toString());
		}
	}

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
		System.out.println(e.getErrorsMap());

		e.validateAndThrow();
	}

	@Test
	public void testMessageFail() throws Exception {
		final Errors e = new Errors();
		final Integer age1 = State.begin(e, "age1", 5).message("m1").fail().getValue();
		assertFalse(e.validate());
		assertNull(age1);
		assertEquals("{age1=m1}", e.getErrorsMap().toString());
		assertEquals("{age1=State [isFailed=true, message=m1, value=5]}", e.getErrorStatesMap().toString());
	}

	@SuppressWarnings("unused")
	@Test
	public void testNulls() throws Exception {
		final Errors e = new Errors();

		final Integer age1 = State.begin(e, "age1", null).message("m1").isNull().isNull().getValue();
		final Integer age2 = State.begin(e, "age2", 5).message("m2").isNull().isNull().getValue();
		final Integer age3 = State.begin(e, "age3", null).message("m3").isNotNull().isNotNull().getValue();
		final Integer age4 = State.begin(e, "age4", 5).message("m4").isNotNull().isNotNull().getValue();

		assertEquals("{age3=m3, age2=m2}", e.getErrorsMap().toString());
	}

	@Test
	public void testOr() {
		final Errors e = new Errors();

		final Integer age1 = Relationals.or(
		/**/State.begin(e, "age1", 5).isNotNull(),
		/**/State.begin(e, "age1", 5).isNotNull()
		/**/).getValue();
		e.validateAndThrow();
		assertEquals(Integer.valueOf(5), age1);

		e.clear();
		final Integer age2 = Relationals.or(
		/**/State.begin(e, "age2", 5).isNotNull(),
		/**/State.begin(e, "age2", 5).isNull()
		/**/).getValue();
		e.validateAndThrow();
		assertEquals(Integer.valueOf(5), age2);

		e.clear();
		final Integer age3 = Relationals.or(
		/**/State.begin(e, "age3", 5).isNull(),
		/**/State.begin(e, "age3", 5).isNotNull()
		/**/).getValue();
		e.validateAndThrow();
		assertEquals(Integer.valueOf(5), age3);

		e.clear();
		Relationals.or(
		/**/State.begin(e, "age4", 5).message("m1").isNull(),
		/**/State.begin(e, "age4", 5).message("m2").isNull()
		/**/).getValue();
		try {
			e.validateAndThrow();
		} catch (final VException e2) {
			assertEquals("{age4=m2}", e2.getErrorsMap().toString());
		}
	}

}

class VException extends RuntimeException {

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

class Errors {

	protected final Map<String, State> errorStatesMap;
	protected final Map<String, String> errorsMap;

	public Errors() {
		errorStatesMap = new HashMap<String, State>();
		this.errorsMap = new HashMap<String, String>();
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

class Relationals {

	public static State or(final State state1, final State state2) {
		if (state1.isFailed) {
			return state2;
		} else if (state2.isFailed) {
			return state1;
		} else {
			return state1;
		}
	}

	public static State and(final State state1, final State state2) {
		if (state1.isFailed) {
			return state1;
		} else if (state2.isFailed) {
			return state2;
		} else {
			return state1;
		}
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
		// System.out.println(this);
	}

	public static State begin(final Errors errors, final String propName, final Object value) {
		return new State(errors, propName, false, "unknown error message", value);
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

	protected State fail() {
		if (isFailed) {
			return this;
		}
		return new State(errors, propName, true, message, value);
	}

	public State message(final String msg) {
		if (isFailed) {
			return this;
		}
		return new State(errors, propName, false, msg, value);
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

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
		try {
			Double.valueOf(value.toString());
			return this;
		} catch (final Exception e) {
			return fail();
		}
	}

	public NumericState asInteger() {
		if (isFailed || value == null) {
			return new NumericState(this);
		}
		try {
			final Integer v = Integer.valueOf(value.toString());
			return new NumericState(errors, propName, isFailed, message, v);
		} catch (final Exception e) {
			return new NumericState(fail());
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

}

class NumericState extends State {

	public NumericState(final State state) {
		super(state.errors, state.propName, state.isFailed, state.message, state.value);
	}

	public NumericState(final Errors errors, final String propName, final boolean isFailed,
			final String message, final Object value) {
		super(errors, propName, isFailed, message, value);
	}

	@Override
	public NumericState message(final String msg) {
		if (isFailed) {
			return this;
		}
		return new NumericState(errors, propName, false, msg, value);
	}

	@Override
	protected NumericState fail() {
		return new NumericState(errors, propName, true, message, value);
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public NumericState numericInRange(final double min, final double max) {
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