package org.orm.mapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mb.criteria.Criteria;
import org.mb.mapper.DataMapper;
import org.mb.query.QueryObject;
import org.mb.record.field.regular.FString;
import org.mb.record.field.regular.primitive.FShort;
import org.mb.session.SessionFactory;
import org.orm.test.EntityTest2;

import static org.junit.Assert.*;

public class AggregatesTest {

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
		SessionFactory.getSession().commit();

		SessionFactory.getSession().open();
		try {
			final QueryObject query = new QueryObject("SELECT COUNT(*) AS VALUE FROM DOG");
			final short n = DataMapper.aggregate(new FShort("VALUE"), query);
			assertEquals(7, n);

			// FInteger field =new FInteger ("VALUE");
			// int n2 = Criteria.select(Dog.class,
			// Aggregate.countAll(field)).getColumnValue(field);
			// assertEquals(7, n2);
		} finally {
			SessionFactory.getSession().closeReadOnly();
		}

		SessionFactory.getSession().open();
		final String q = "SELECT NAME FROM DOG WHERE AGE IN (SELECT MAX(AGE) FROM DOG)";
		try {
			final String n = DataMapper.aggregate(new FString("NAME"), new QueryObject(q));
			assertEquals("din", n);
		} finally {
			SessionFactory.getSession().closeReadOnly();
		}

		SessionFactory.getSession().open();
		try {
			final String n = Criteria.query(q).getColumnValue(new FString("NAME"));
			assertEquals("din", n);
		} finally {
			SessionFactory.getSession().closeReadOnly();
		}

	}

}
