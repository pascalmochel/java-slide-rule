package org.morm.test;

import org.junit.Test;
import org.morm.datasource.HsqldbDataSourceFactory;
import org.morm.logging.LogFactory;
import org.morm.logging.SingleLineFormatter;
import org.morm.mapper.DataMapper;
import org.morm.session.SessionFactory;

import java.util.logging.Handler;
import java.util.logging.Level;

public class EntityTest2 {

	static {

		final SingleLineFormatter singleLineFormatter = new SingleLineFormatter();
		for (final Handler h : LogFactory.getRootLogger().getHandlers()) {
			h.setFormatter(singleLineFormatter);
			h.setLevel(Level.FINE);
		}

		LogFactory.getRootLogger().setLevel(Level.FINE);
		new SessionFactory().setDataSource(new HsqldbDataSourceFactory().getDataSource());
	}

	@Test
	public void testname() throws Exception {

		// public static void main(String[] args) {

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

			{
				System.out.println("====================================");

				DataMapper.executeDDL("INSERT INTO DOG (ID_DOG,NAME,AGE) VALUES (500,'din',9)");
				DataMapper
						.executeDDL("INSERT INTO RABBIT (ID_RABBIT,NAME,AGE,NUM_DOG) VALUES (600,'cornill',5, 500)");
				{
					new Rabbit();
					new Dog();
				}

				final Rabbit r = Rabbit.findById(600);
				System.out.println(r);
				System.out.println(r.getDog());
				System.out.println(r);

				System.out.println("====================================");

				final Dog d = Dog.findById(500);
				System.out.println(d);
				System.out.println(d.getRabbits());
				System.out.println(d);

				System.out.println("====================================");
				System.out.println(r);
				r.store();
				System.out.println("====================================");
				System.out.println(d);
				d.store();

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
