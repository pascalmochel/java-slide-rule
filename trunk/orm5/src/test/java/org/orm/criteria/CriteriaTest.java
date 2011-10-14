package org.orm.criteria;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mb.mapper.DataMapper;
import org.mb.session.SessionFactory;
import org.orm.test.EntityTest2;
import org.orm.test.ent.Dog;

import java.util.List;

import static org.junit.Assert.*;
import static org.mb.criteria.Criteria.*;
import static org.mb.criteria.order.Order.*;
import static org.mb.criteria.restriction.Restriction.*;

public class CriteriaTest {

	static {
		new EntityTest2();
	}

	@Before
	public void before() {
		SessionFactory.getSession().open();

		DataMapper.executeDDLIgnoringErrors("DROP TABLE DOG");

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
		DataMapper.executeDDL("DROP TABLE DOG");
		SessionFactory.getSession().commit();
	}

	@Test
	public void testInsertDogInsertRabbits() {

		SessionFactory.getSession().open();
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (50,'din',10)");
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (51,'faria',9)");
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (52,'gossa',8)");
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (53,'blanca',7)");
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (54,'negra',6)");
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (55,'pelut',5)");
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (56,'chucho',3)");

		try {
			final List<Dog> d1 = selectBy(in(Dog.name, "faria", "gossa")).list(Dog.class);

			assertEquals(
			/**/"[[ID_DOG=51, NAME=faria, AGE=9, [...]], " +
			/**/"[ID_DOG=52, NAME=gossa, AGE=8, [...]]]"
			/**/, d1.toString());

			final List<Dog> d2 = selectBy(like(Dog.name, "%egra%")).list(Dog.class);

			assertEquals(
			/**/"[[ID_DOG=54, NAME=negra, AGE=6, [...]]]"
			/**/, d2.toString());

			final List<Dog> d3 = selectAll().orderBy(asc(Dog.age), asc(Dog.name)).list(Dog.class);

			assertEquals(
			/**/"[[ID_DOG=56, NAME=chucho, AGE=3, [...]], " +
			/**/"[ID_DOG=55, NAME=pelut, AGE=5, [...]], " +
			/**/"[ID_DOG=54, NAME=negra, AGE=6, [...]], " +
			/**/"[ID_DOG=53, NAME=blanca, AGE=7, [...]], " +
			/**/"[ID_DOG=52, NAME=gossa, AGE=8, [...]], " +
			/**/"[ID_DOG=51, NAME=faria, AGE=9, [...]], " +
			/**/"[ID_DOG=50, NAME=din, AGE=10, [...]]]"
			/**/, d3.toString());

			final List<Dog> d4 = selectBy(between(Dog.age, 3, 5)).list(Dog.class);

			assertEquals(
			/**/"[[ID_DOG=55, NAME=pelut, AGE=5, [...]], " +
			/**/"[ID_DOG=56, NAME=chucho, AGE=3, [...]]]"
			/**/, d4.toString());

			final List<Dog> d5 = selectBy(not(lt(Dog.age, 10))).list(Dog.class);

			assertEquals(
			/**/"[[ID_DOG=50, NAME=din, AGE=10, [...]]]"
			/**/, d5.toString());

			final List<Dog> d6 = selectBy(sqlClause("1=1 LIMIT 2")).list(Dog.class);

			assertEquals(2, d6.size());

			final List<Dog> d7 = selectBy(or(and(lt(Dog.age, 8), gt(Dog.age, 6)), eq(Dog.name, "faria")))
					.list(Dog.class);

			assertEquals(
			/**/"[[ID_DOG=51, NAME=faria, AGE=9, [...]], [ID_DOG=53, NAME=blanca, AGE=7, [...]]]"
			/**/, d7.toString());

		} finally {
			SessionFactory.getSession().rollback();
		}
	}

}
