package org.morm.test;

import org.junit.Test;
import org.morm.criteria.impl.OrderBy;
import org.morm.datasource.HsqldbDataSourceFactory;
import org.morm.exception.SormException;
import org.morm.mapper.DataMapper;
import org.morm.record.Entity;
import org.morm.session.SessionFactory;

import static org.junit.Assert.*;
import static org.morm.criteria.Criteria.*;

public class EntityTest {

	static {
		new SessionFactory().setDataSource(new HsqldbDataSourceFactory().getDataSource());
	}

	@Test
	public void testname() throws Exception {

		{
			SessionFactory.getCurrentSession().beginTransaction();

			DataMapper.executeDDLIgnoringErrors("DROP TABLE RABBIT");
			DataMapper.executeDDLIgnoringErrors("DROP TABLE DOG");

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

		}
		{
			new Dog();
			final Rabbit X = new Rabbit();

			final Rabbit a = new Rabbit(null, "din", 9);
			a.store();

			System.out.println(a);

			try {
				new Rabbit().loadById(4);
				fail();
			} catch (final SormException e2) {
				// assertEquals("java.lang.RuntimeException: no row produced",
				// e2.getMessage());
			}

			final Rabbit rabbit = Entity.loadById(Rabbit.class, 100);
			System.out.println(rabbit);

			rabbit.setName("jou");
			rabbit.store();

			System.out.println(new Rabbit().loadById(100));
			System.out.println(new Rabbit().loadAll());

			System.out.println(new Rabbit().loadBy(eq(Rabbit.id, 4)));
			System.out.println(new Rabbit().loadBy(eq(Rabbit.id, 100)));
			System.out.println(new Rabbit().loadBy(and(eq(Rabbit.id, 5), eq(Rabbit.name, "jou"))));

			System.out.println(
			/**/new Rabbit().loadBy(eq(Rabbit.id, 100), orderBy(OrderBy.ASC, Rabbit.name, Rabbit.id))
			/**/);

			System.out.println(X.count(eq(Rabbit.id, 4)));
			System.out.println(X.count(eq(Rabbit.id, 100)));

			rabbit.delete();

			System.out.println(new Rabbit().loadAll());

			System.out.println(a.getDog());

			{
				System.out.println("====================================");

				DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (500,'din',9)");
				DataMapper
						.executeDDL("INSERT INTO RABBIT (ID_RABBIT,NAME,AGE,NUM_DOG) VALUES (600,'cornill',5, 500)");

				final Rabbit r = new Rabbit().loadById(600);
				System.out.println(r);
				System.out.println(r.getDog());
				System.out.println(r);

			}

			SessionFactory.getCurrentSession().rollback();
		}
		{
			SessionFactory.getCurrentSession().beginTransaction();
			DataMapper.executeDDL("DROP TABLE RABBIT");
			DataMapper.executeDDL("DROP TABLE DOG");
			SessionFactory.getCurrentSession().commit();
		}
	}
}
