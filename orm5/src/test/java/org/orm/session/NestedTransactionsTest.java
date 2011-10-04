package org.orm.session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.orm.criteria.Criteria;
import org.orm.exception.OrmException;
import org.orm.mapper.DataMapper;
import org.orm.record.field.impl.primitive.FLong;
import org.orm.test.EntityTest2;
import org.orm.test.ent.Dog;

import static org.junit.Assert.*;

/**
 * verifica exhaustivament les transaccions anidades, i de la propagació
 * d'excepcions que causa el rollback massiu.
 */
public class NestedTransactionsTest {

	static {
		new EntityTest2();
	}

	@Before
	public void before() {
		SessionFactory.getSession().open();

		DataMapper.executeDDLIgnoringErrors("DROP TABLE DOG");
		DataMapper.executeDDLIgnoringErrors("DROP TABLE RABBIT");

		DataMapper.executeDDL(
		/**/"CREATE TABLE RABBIT (" +
		/**/"ID_RABBIT INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY," +
		/**/"NAME VARCHAR(20)," +
		/**/"AGE INTEGER," +
		/**/"NUM_DOG INTEGER)"
		/**/);
		DataMapper.executeDDL(
		/**/"CREATE TABLE DOG (" +
		/**/"ID_DOG INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY," +
		/**/"NAME VARCHAR(20)," +
		/**/"AGE INTEGER)"
		/**/);
		SessionFactory.getSession().commit();
	}

	@After
	public void after() {
		SessionFactory.getSession().open();
		DataMapper.executeDDL("DROP TABLE RABBIT");
		DataMapper.executeDDL("DROP TABLE DOG");
		SessionFactory.getSession().commit();
	}

	@Test
	public void testProgram1() {
		testMachine("{0+1{+2}1+2}{0}");
	}

	@Test
	public void testProgram2() {
		testMachine("{0+1{+2]2+3}{0}");
	}

	/**
	 * aquest cas no pot ser, ja que en cas d'error, es fa rollback i l'excepció
	 * es propaga causant també rollback als altres nivells
	 */
	@Test
	public void testProgram3() {
		testMachine("{0+1{+2}1+2]{2}");
	}

	@Test
	public void testProgram4() {
		testMachine("{0+1{+2]2+3]{3}");
	}

	@Test
	public void testProgram5() {
		testMachine("{0+1{+2{+3]3]3+4]{4}");
	}

	@Test
	public void testProgram7() {
		testMachine("{0+1{+2{+3}2}1+2]{2}");
	}

	@Test
	public void testProgram8() {
		testMachine("{0+1{+2{+3}2}1+2}{0}");
	}

	@Test
	public void testProgram9() {
		testMachine("{0+1{+2{+3]3+4]4+5}{0}");
	}

	public void testMachine(final String symbolProgram) {

		try {
			for (int i = 0; i < symbolProgram.length(); i++) {
				execSymbol(symbolProgram.charAt(i));
			}
		} catch (final Exception e) {
			throw new OrmException("in program: " + symbolProgram, e);
		}
	}

	private void execSymbol(final char c) {
		switch (c) {
		case '{':
			SessionFactory.getSession().open();
			break;
		case '+':
			new Dog(null, "jou", 29).store();
			break;
		case '}':
			SessionFactory.getSession().rollback();
			break;
		case ']':
			SessionFactory.getSession().commit();
			break;
		default:
			Long count = Criteria.query("SELECT COUNT(*) AS VALUE FROM DOG").getColumnValue(
					new FLong("VALUE"));
			assertEquals(Long.valueOf(String.valueOf(c)), count);
		}
	}

}
