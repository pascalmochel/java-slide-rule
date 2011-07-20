package org.frijoles.binder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

public class State {

	protected final ValidationErrors validationErrors;

	protected final boolean isFailed;
	protected final String message;

	protected final String propName;
	protected final Object value;

	protected State(final ValidationErrors validationErrors, final String propName, final Object value, final boolean isFailed,
			final String message) {
		super();
		this.validationErrors = validationErrors;
		this.propName = propName;
		this.isFailed = isFailed;
		this.message = message;
		this.value = value;
	}

	public static State begin(final ValidationErrors validationErrors, final String propName, final Object value) {
		return new State(validationErrors, propName, value, false, "unknown error message");
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue() {
		if (isFailed) {
			validationErrors.registerError(propName, this);
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

	public State fail() {
		if (isFailed) {
			return this;
		}
		return new State(validationErrors, propName, value, true, message);
	}

	public State message(final String msg) {
		if (isFailed) {
			return this;
		}
		return new State(validationErrors, propName, value, false, msg);
	}

	public State mutateValue(final Object newValue) {
		if (isFailed) {
			return this;
		}
		return new State(validationErrors, propName, newValue, isFailed, message);
	}

	public State negate() {
		if (isFailed) {
			return new State(validationErrors, propName, value, false, message);
		} else {
			return fail();
		}
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public State rejectIf(final boolean reject) {
		if (reject && !isFailed) {
			return fail();
		} else {
			return this;
		}
	}

	public State isNull() {
		return rejectIf(value != null);
	}

	public State isNotNull() {
		return rejectIf(value == null);
	}

	public State isNumeric() {
		if (isFailed) {
			return this;
		}
		try {
			Double.valueOf(value.toString());
			return this;
		} catch (final NumberFormatException e) {
			return fail();
		}
	}

	public State asInteger() {
		if (isFailed || value == null) {
			return this;
		}
		try {
			final Integer v = Integer.valueOf(value.toString());
			return mutateValue(v);
		} catch (final NumberFormatException e) {
			return fail();
		}
	}

	public State asLong() {
		if (isFailed || value == null) {
			return this;
		}
		try {
			final Long v = Long.valueOf(value.toString());
			return mutateValue(v);
		} catch (final NumberFormatException e) {
			return fail();
		}
	}

	public State asFloat() {
		if (isFailed || value == null) {
			return this;
		}
		try {
			final Float v = Float.valueOf(value.toString());
			return mutateValue(v);
		} catch (final NumberFormatException e) {
			return fail();
		}
	}

	public State asDouble() {
		if (isFailed || value == null) {
			return this;
		}
		try {
			final Double v = Double.valueOf(value.toString());
			return mutateValue(v);
		} catch (final NumberFormatException e) {
			return fail();
		}
	}

	public State asBoolean() {
		if (isFailed || value == null) {
			return this;
		}
		try {
			final Boolean v = Boolean.valueOf(value.toString());
			return mutateValue(v);
		} catch (final Exception e) {
			return fail();
		}
	}

	public State asDate(final String format) {
		if (isFailed || value == null) {
			return this;
		}
		try {
			final DateFormat formatter = new SimpleDateFormat(format);
			final Date date = formatter.parse(value.toString());
			return mutateValue(date);
		} catch (final ParseException e) {
			return fail();
		}
	}

	public State asString() {
		if (isFailed || value == null) {
			return this;
		}
		try {
			return mutateValue(value.toString());
		} catch (final Exception e) {
			return fail();
		}
	}

	public State minValue(final double min) {
		if (value == null) {
			return this;
		}
		if (!(value instanceof Number)) {
			return fail();
		}
		final double v = ((Number) value).doubleValue();
		return rejectIf(v < min);
	}

	public State maxValue(final double max) {
		if (value == null) {
			return this;
		}
		if (!(value instanceof Number)) {
			return fail();
		}
		final double v = ((Number) value).doubleValue();
		return rejectIf(v > max);
	}

	public State minLength(final int length) {
		if (value == null) {
			return this;
		}
		return rejectIf(value.toString().length() < length);
	}

	public State maxLength(final int length) {
		if (value == null) {
			return this;
		}
		return rejectIf(value.toString().length() > length);
	}

	public State hasLength(final int length) {
		if (value == null) {
			return this;
		}
		return rejectIf(value.toString().length() != length);
	}

	public State equalsTo(final Object o) {
		if (value == null) {
			return this;
		}
		return rejectIf(!value.equals(o));
	}

	public State notEqualsTo(final Object o) {
		if (value == null) {
			return this;
		}
		return rejectIf(value.equals(o));
	}

	public State equalsWithTolerance(final Number n, final double tolerance) {
		if (value == null) {
			return this;
		}
		if (!(value instanceof Number)) {
			return fail();
		}
		try {
			final double a = n.doubleValue();
			final double b = ((Number) value).doubleValue();
			if (Math.abs(a - b) > tolerance) {
				return fail();
			}
			return this;
		} catch (final Exception e) {
			return fail();
		}
	}

	public State in(final Object... os) {
		if (isFailed || value == null) {
			return this;
		}
		for (final Object o : os) {
			if (value.equals(o)) {
				return this;
			}
		}
		return fail();
	}

	public State notIn(final Object... os) {
		if (isFailed || value == null) {
			return this;
		}
		for (final Object o : os) {
			if (value.equals(o)) {
				return fail();
			}
		}
		return this;
	}

}
