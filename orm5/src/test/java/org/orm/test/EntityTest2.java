package org.orm.test;

import org.junit.Test;
import org.orm.datasource.HsqldbDataSourceFactory;
import org.orm.logging.LogFactory;
import org.orm.logging.SingleLineFormatter;
import org.orm.mapper.DataMapper;
import org.orm.record.Entity;
import org.orm.session.SessionFactory;
import org.orm.test.ent.Dog;
import org.orm.test.ent.Rabbit;

import java.util.logging.Handler;
import java.util.logging.Level;

import static org.junit.Assert.*;

public class EntityTest2 {

	static {

		final SingleLineFormatter singleLineFormatter = new SingleLineFormatter();
		for (final Handler h : LogFactory.getRootLogger().getHandlers()) {
			h.setFormatter(singleLineFormatter);
			h.setLevel(Level.FINE);
		}
		LogFactory.getRootLogger().setLevel(Level.FINE);

		SessionFactory.setDataSource(new HsqldbDataSourceFactory().getDataSource());
	}

	@Test
	public void testname() throws Exception {

		{
			SessionFactory.getSession().open();

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

			SessionFactory.getSession().commit();

		}
		{

			{
				SessionFactory.getSession().open();

				System.out.println("====================================");

				DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (500,'din',9)");
				DataMapper
						.executeDDL("INSERT INTO RABBIT (ID_RABBIT,NAME,AGE,NUM_DOG) VALUES (600,'cornill',5, 500)");

				final Rabbit r = Entity.loadById(Rabbit.class, 600);
				System.out.println(r);
				System.out.println(r.getDog());
				System.out.println(r);

				System.out.println("====================================");
				SessionFactory.getSession().getIdentityMap().clear();
				SessionFactory.getSession().getIdCache().clear();

				final Dog d = Entity.loadById(Dog.class, 500);
				assertEquals("[ID_DOG=500, NAME=din, AGE=9, [...]]", d.toString());
				System.out.println(d.getRabbits());
				assertEquals(
						"[ID_DOG=500, NAME=din, AGE=9, [[ID_RABBIT=600, NAME=cornill, AGE=5, NUM_DOG=500=>[...]]]]",
						d.toString());

				System.out.println("====================================");
				r.store();
				System.out.println("====================================");
				assertEquals(
						"[ID_DOG=500, NAME=din, AGE=9, [[ID_RABBIT=600, NAME=cornill, AGE=5, NUM_DOG=500=>[...]]]]",
						d.toString());
				d.store();
				assertEquals(
						"[ID_DOG=500, NAME=din, AGE=9, [[ID_RABBIT=600, NAME=cornill, AGE=5, NUM_DOG=500=>[...]]]]",
						d.toString());

				SessionFactory.getSession().commit();

			}

		}
		{
			SessionFactory.getSession().open();
			DataMapper.executeDDL("DROP TABLE RABBIT");
			DataMapper.executeDDL("DROP TABLE DOG");
			SessionFactory.getSession().commit();
		}
	}
}
