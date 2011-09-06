package org.morm.record.identity.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.morm.datasource.HsqldbDataSourceFactory;
import org.morm.mapper.DataMapper;
import org.morm.session.SessionFactory;
import org.morm.test.EntityTest2;

import static org.junit.Assert.*;

public class NoKeyTest {

	static {
		new SessionFactory().setDataSource(new HsqldbDataSourceFactory().getDataSource());
		new EntityTest2();
	}

	@Before
	public void before() {
		SessionFactory.getCurrentSession().beginTransaction();

		DataMapper.executeDDLIgnoringErrors("DROP TABLE DOG");

		DataMapper.executeDDL(
				/**/"CREATE TABLE DOG (" +
				/**/"ID_DOG INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY," +
				/**/"NAME VARCHAR(20)," +
				/**/"AGE INTEGER)"
		/**/);

		SessionFactory.getCurrentSession().commit();
	}

	@After
	public void after() {

		SessionFactory.getCurrentSession().beginTransaction();
		DataMapper.executeDDL("DROP TABLE DOG");
		SessionFactory.getCurrentSession().commit();
	}

	@Test
	public void testname() throws Exception {
		SessionFactory.getCurrentSession().beginTransaction();

		new NoKeyDog(5, "din", 9).insert();
		assertEquals("[[ID_DOG=5, NAME=din, AGE=9]]", new NoKeyDog().loadAll().toString());

		SessionFactory.getCurrentSession().rollback();
	}

}
