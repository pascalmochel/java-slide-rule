package org.orm.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mb.criteria.Criteria;
import org.mb.mapper.DataMapper;
import org.mb.session.SessionFactory;
import org.orm.test.ent.Dog;

import static org.junit.Assert.*;

public class RecursiveTest {

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
	public void test1() {

		SessionFactory.getSession().open();
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (500,'din',9)");
		DataMapper
				.executeDDL("INSERT INTO RABBIT (ID_RABBIT,NAME,AGE,NUM_DOG) VALUES (600,'cornill',5, 500)");

		try {
			/**
			 * demostra que havent-hi recursió infinita (s'evidencia amb el
			 * fallo en toString()), el mètode store() està controlat
			 */
			final Dog d = Criteria.selectById(Dog.class, 500);
			try {
				d.getRabbits().get(0).getDog().toString();
				fail();
			} catch (final StackOverflowError e) {
			}

			d.store();

		} finally {
			SessionFactory.getSession().rollback();
		}
	}

}
