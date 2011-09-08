package org.morm.record.field.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.morm.datasource.HsqldbDataSourceFactory;
import org.morm.mapper.DataMapper;
import org.morm.record.Entity;
import org.morm.session.SessionFactory;
import org.morm.test.EntityTest2;

public class EnumTest {

	static {
		new EntityTest2();
		new SessionFactory().setDataSource(new HsqldbDataSourceFactory().getDataSource());
	}

	@Before
	public void before() {
		SessionFactory.getCurrentSession().open();

		DataMapper.executeDDLIgnoringErrors("DROP TABLE DOG");

		DataMapper.executeDDL(
		/**/"CREATE TABLE DOG (" +
		/**/"ID_DOG INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY," +
		/**/"NAME VARCHAR(20)," +
		/**/"COLOR VARCHAR(20))"
		/**/);

		SessionFactory.getCurrentSession().commit();
	}

	@After
	public void after() {

		SessionFactory.getCurrentSession().open();
		DataMapper.executeDDL("DROP TABLE DOG");
		SessionFactory.getCurrentSession().commit();
	}

	@Test
	public void testname() throws Exception {
		SessionFactory.getCurrentSession().open();

		try {

			final EnumDog d = new EnumDog(null, "din", EColor.RED);
			d.store();
			assertEquals("[[ID_DOG=100, NAME=din, COLOR=RED]]", Entity.loadAll(EnumDog.class).toString());

			d.setColor(EColor.WHITE);
			d.store();
			assertEquals("[[ID_DOG=100, NAME=din, COLOR=WHITE]]", Entity.loadAll(EnumDog.class).toString());

		} finally {
			SessionFactory.getCurrentSession().rollback();
		}
	}

}
