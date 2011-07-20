package org.frijoles.binder.test;

import org.frijoles.binder.ValidationErrors;
import org.frijoles.binder.Relationals;
import org.frijoles.binder.State;
import org.frijoles.binder.ValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class Vtest3 {

	@Test
	public void testnameDuh() throws Exception {
		final ValidationErrors e = new ValidationErrors();

		final Integer age = State.begin(e, "age", 28).getValue();
		assertEquals(Integer.valueOf(28), age);
		e.validateAndThrow();
	}

	@Test
	public void testnameBindOk() throws Exception {
		final ValidationErrors e = new ValidationErrors();

		final Integer age = State.begin(e, "age", "28").asInteger().getValue();
		assertEquals(Integer.valueOf(28), age);
		e.validateAndThrow();
	}

	@Test
	public void testnameBindFails() throws Exception {
		final ValidationErrors e = new ValidationErrors();

		final Integer age = State.begin(e, "age", "jou").message("is not an integer").asInteger().getValue();
		assertNull(age);
		try {
			e.validateAndThrow();
		} catch (final ValidationException e2) {
			assertEquals("{age=is not an integer}", e2.getErrorsMap().toString());
		}
	}

	@Test
	public void testnameBindAndValidateOk() throws Exception {
		final ValidationErrors e = new ValidationErrors();

		final Integer age = State.begin(e, "age", "28").asInteger().maxValue(130).getValue();
		assertEquals(Integer.valueOf(28), age);
		e.validateAndThrow();
	}

	@Test
	public void testnameBindAndValidateFails() throws Exception {
		final ValidationErrors e = new ValidationErrors();

		final Integer age = State.begin(e, "age", "800").asInteger().message("must be in 0..130").maxValue(
				130).getValue();
		assertNull(age);
		try {
			e.validateAndThrow();
		} catch (final ValidationException e2) {
			assertEquals("{age=must be in 0..130}", e2.getErrorsMap().toString());
		}
	}

	@Test
	public void testname0() throws Exception {

		final ValidationErrors e = new ValidationErrors();

		final String name = State.begin(e, "name", "mhc")
		/**/.message("must be not null")
		/**/.isNotNull()
		/**/.message("must have 3..6 length")
		/**/.minLength(3)
		/**/.maxLength(6)
		/**/.getValue()
		/**/;

		final Integer age = State.begin(e, "age", "5")
		/**/.message("must be not null")
		/**/.isNotNull()
		/**/.message("must be numeric")
		/**/.isNumeric()
		/**/.asInteger()
		/**/.message("must values 0..130")
		/**/.minValue(0)
		/**/.maxValue(130)
		/**/.getValue()
		/**/;

		System.out.println(name + "," + age);
		System.out.println(e.getErrorsMap());

		e.validateAndThrow();
	}

	@Test
	public void testMessageFail() throws Exception {
		final ValidationErrors e = new ValidationErrors();
		final Integer age1 = State.begin(e, "age1", 5).message("m1").fail().getValue();
		assertFalse(e.validate());
		assertNull(age1);
		assertEquals("{age1=m1}", e.getErrorsMap().toString());
		assertEquals("{age1=State [isFailed=true, message=m1, value=5]}", e.getErrorStatesMap().toString());
	}

	@SuppressWarnings("unused")
	@Test
	public void testNulls() throws Exception {
		final ValidationErrors e = new ValidationErrors();

		final Integer age1 = State.begin(e, "age1", null).message("m1").isNull().isNull().getValue();
		final Integer age2 = State.begin(e, "age2", 5).message("m2").isNull().isNull().getValue();
		final Integer age3 = State.begin(e, "age3", null).message("m3").isNotNull().isNotNull().getValue();
		final Integer age4 = State.begin(e, "age4", 5).message("m4").isNotNull().isNotNull().getValue();

		assertEquals("{age3=m3, age2=m2}", e.getErrorsMap().toString());
	}

	@Test
	public void testOr() {
		final ValidationErrors e = new ValidationErrors();

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
		} catch (final ValidationException e2) {
			assertEquals("{age4=m2}", e2.getErrorsMap().toString());
		}
	}

	@Test
	public void testNegate() throws Exception {
		final ValidationErrors e = new ValidationErrors();

		final String name = State.begin(e, "name", "mhc").fail().negate().getValue();
		assertEquals("mhc", name);
	}

	// @Test
	// public void testname() throws Exception {
	//
	// new DateFormat();
	// final DateFormat a;
	//
	// }

}
