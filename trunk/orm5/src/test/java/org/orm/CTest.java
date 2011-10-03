package org.orm;

import static org.junit.Assert.assertEquals;
import static org.orm.criteria.Criteria.and;
import static org.orm.criteria.Criteria.between;
import static org.orm.criteria.Criteria.eq;
import static org.orm.criteria.Criteria.gt;
import static org.orm.criteria.Criteria.in;
import static org.orm.criteria.Criteria.like;
import static org.orm.criteria.Criteria.lt;
import static org.orm.criteria.Criteria.not;
import static org.orm.criteria.Criteria.or;
import static org.orm.criteria.Criteria.sqlClause;
import static org.orm.criteria.impl.Order.asc;
import static org.orm.criteria.impl.Order.desc;
import static org.orm.criteria2.Crit.select;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.orm.mapper.DataMapper;
import org.orm.query.IQueryObject;
import org.orm.session.SessionFactory;
import org.orm.test.Dog;
import org.orm.test.EntityTest2;

public class CTest {

	@Test
	public void testname() throws Exception {

		{
			final IQueryObject r = select(Dog.class).where(eq(Dog.name, "din")).renderQuery();

			assertEquals("SELECT * FROM DOG WHERE NAME=? -- [din]", r.toString());
		}
		{
			final IQueryObject r = select(Dog.class).where(eq(Dog.name, "din")).orderBy(asc(Dog.name),
					desc(Dog.age)).renderQuery();

			assertEquals("SELECT * FROM DOG WHERE NAME=? ORDER BY NAME ASC,AGE DESC -- [din]", r.toString());
		}
	}

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
	public void test() {

		SessionFactory.getSession().open();
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (50,'din',10)");
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (51,'faria',9)");
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (52,'gossa',8)");
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (53,'blanca',7)");
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (54,'negra',6)");
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (55,'pelut',5)");
		DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (56,'chucho',3)");

		try {

			Dog d0 = select(Dog.class)
			/**/.where(eq(Dog.name, "din"))
			/**/.orderBy(asc(Dog.name))
			/**/.getUnique();

			assertEquals("[ID_DOG=50, NAME=din, AGE=10, [...]]", d0.toString());

			final List<Dog> d1 = select(Dog.class).where(in(Dog.name, "faria", "gossa")).get();

			assertEquals(
			/**/"[[ID_DOG=51, NAME=faria, AGE=9, [...]], " +
			/**/"[ID_DOG=52, NAME=gossa, AGE=8, [...]]]"
			/**/, d1.toString());

			final List<Dog> d2 = select(Dog.class).where(like(Dog.name, "%egra%")).get();

			assertEquals(
			/**/"[[ID_DOG=54, NAME=negra, AGE=6, [...]]]"
			/**/, d2.toString());

			final List<Dog> d3 = select(Dog.class).orderBy(asc(Dog.age), asc(Dog.name)).get();

			assertEquals(
			/**/"[[ID_DOG=56, NAME=chucho, AGE=3, [...]], " +
			/**/"[ID_DOG=55, NAME=pelut, AGE=5, [...]], " +
			/**/"[ID_DOG=54, NAME=negra, AGE=6, [...]], " +
			/**/"[ID_DOG=53, NAME=blanca, AGE=7, [...]], " +
			/**/"[ID_DOG=52, NAME=gossa, AGE=8, [...]], " +
			/**/"[ID_DOG=51, NAME=faria, AGE=9, [...]], " +
			/**/"[ID_DOG=50, NAME=din, AGE=10, [...]]]"
			/**/, d3.toString());

			final List<Dog> d4 = select(Dog.class).where(between(Dog.age, 3, 5)).get();

			assertEquals(
			/**/"[[ID_DOG=55, NAME=pelut, AGE=5, [...]], " +
			/**/"[ID_DOG=56, NAME=chucho, AGE=3, [...]]]"
			/**/, d4.toString());

			final List<Dog> d5 = select(Dog.class).where(not(lt(Dog.age, 10))).get();

			assertEquals(
			/**/"[[ID_DOG=50, NAME=din, AGE=10, [...]]]"
			/**/, d5.toString());

			final List<Dog> d6 = select(Dog.class).where(sqlClause("1=1 LIMIT 2")).get();

			System.out.println(d6);
			assertEquals(2, d6.size());

			final List<Dog> d7 = select(Dog.class)
					.where(or(and(lt(Dog.age, 8), gt(Dog.age, 6)), eq(Dog.name, "faria"))).get();

			assertEquals(
			/**/"[[ID_DOG=51, NAME=faria, AGE=9, [...]], [ID_DOG=53, NAME=blanca, AGE=7, [...]]]"
			/**/, d7.toString());

		} finally {
			SessionFactory.getSession().rollback();
		}
	}
}
