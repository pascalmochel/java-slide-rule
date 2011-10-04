package org.orm.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.orm.mapper.DataMapper;
import org.orm.record.Entity;
import org.orm.session.SessionFactory;
import org.orm.test.ent.Dog;
import org.orm.test.ent.Rabbit;

import static org.junit.Assert.*;

public class ATest {

	static {
		new EntityTest2();
	}

	@Test
	public void test() {

		SessionFactory.getSession().open();

		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (100,'d1',1)");
		DataMapper.executeDDL("INSERT INTO RABBIT (ID_RABBIT,NAME,AGE,NUM_DOG) VALUES (200,'c11',1, 100)");
		DataMapper.executeDDL("INSERT INTO RABBIT (ID_RABBIT,NAME,AGE,NUM_DOG) VALUES (201,'c12',1, 100)");
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (101,'d2',2)");
		DataMapper.executeDDL("INSERT INTO RABBIT (ID_RABBIT,NAME,AGE,NUM_DOG) VALUES (202,'c21',2, 101)");
		DataMapper.executeDDL("INSERT INTO RABBIT (ID_RABBIT,NAME,AGE,NUM_DOG) VALUES (203,'c22',2, 101)");

		try {
			final Dog d1 = Entity.loadById(Dog.class, 100);
			d1.getRabbits();
			final Dog d2 = Entity.loadById(Dog.class, 101);
			d2.getRabbits();

			assertEquals(
			/**/"[ID_DOG=100, NAME=d1, AGE=1, " +
			/**/"[[ID_RABBIT=200, NAME=c11, AGE=1, NUM_DOG=100=>[...]], " +
			/**/"[ID_RABBIT=201, NAME=c12, AGE=1, NUM_DOG=100=>[...]]]]"
			/**/, d1.toString());
			assertEquals(
			/**/"[ID_DOG=101, NAME=d2, AGE=2, " +
			/**/"[[ID_RABBIT=202, NAME=c21, AGE=2, NUM_DOG=101=>[...]], " +
			/**/"[ID_RABBIT=203, NAME=c22, AGE=2, NUM_DOG=101=>[...]]]]"
			/**/, d2.toString());

			SessionFactory.getSession().getIdentityMap().clear();

			final Rabbit r1 = Entity.loadById(Rabbit.class, 200);
			r1.getDog();
			final Rabbit r2 = Entity.loadById(Rabbit.class, 201);
			r2.getDog();
			final Rabbit r3 = Entity.loadById(Rabbit.class, 202);
			r3.getDog();
			final Rabbit r4 = Entity.loadById(Rabbit.class, 203);
			r4.getDog();

			assertEquals(
			/**/"[ID_RABBIT=200, NAME=c11, AGE=1, NUM_DOG=100=>[ID_DOG=100, NAME=d1, AGE=1, [...]]]"
			/**/, r1.toString());
			assertEquals(
			/**/"[ID_RABBIT=201, NAME=c12, AGE=1, NUM_DOG=100=>[ID_DOG=100, NAME=d1, AGE=1, [...]]]"
			/**/, r2.toString());
			assertEquals(
			/**/"[ID_RABBIT=202, NAME=c21, AGE=2, NUM_DOG=101=>[ID_DOG=101, NAME=d2, AGE=2, [...]]]"
			/**/, r3.toString());
			assertEquals(
			/**/"[ID_RABBIT=203, NAME=c22, AGE=2, NUM_DOG=101=>[ID_DOG=101, NAME=d2, AGE=2, [...]]]"
			/**/, r4.toString());

			r1.getDog().getRabbits();
			try {
				r1.toString();
				fail();
			} catch (final StackOverflowError e) {
			}

			SessionFactory.getSession().commit();
		} catch (final Exception e) {
			SessionFactory.getSession().rollback();
		}
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

}