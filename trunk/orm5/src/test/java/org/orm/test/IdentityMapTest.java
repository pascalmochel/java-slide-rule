package org.orm.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.orm.criteria.Criteria;
import org.orm.mapper.DataMapper;
import org.orm.session.SessionFactory;
import org.orm.test.ent.Dog;

public class IdentityMapTest {

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

		try {

			final Dog d1 = Criteria.selectById(Dog.class, 500);
			final Dog d2 = Criteria.selectById(Dog.class, 500);

			assertEquals("[ID_DOG=500, NAME=din, AGE=9, [...]]", d1.toString());
			assertTrue(d1 == d2);

		} finally {
			SessionFactory.getSession().rollback();
		}
	}

	@Test
	public void test2() {

		SessionFactory.getSession().open();

		try {

			final Dog d1 = new Dog(null, "chucho", 3);
			d1.store();
			final Dog d2 = Criteria.selectById(Dog.class, 100);

			assertEquals("[ID_DOG=100, NAME=chucho, AGE=3, [...]]", d1.toString());
			assertTrue(d1 == d2);

		} finally {
			SessionFactory.getSession().rollback();
		}
	}

}
