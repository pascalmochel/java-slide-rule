package org.orm.record.identity.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.orm.criteria.Criteria;
import org.orm.datasource.HsqldbDataSourceFactory;
import org.orm.mapper.DataMapper;
import org.orm.session.SessionFactory;
import org.orm.test.EntityTest2;

import static org.junit.Assert.*;

public class NoKeyTest {

	static {
		new EntityTest2();
		SessionFactory.setDataSource(new HsqldbDataSourceFactory().getDataSource());
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
	public void testname() throws Exception {
		SessionFactory.getSession().open();

		new NoKeyDog(5, "din", 9).insert();
		assertEquals("[[ID_DOG=5, NAME=din, AGE=9]]", Criteria.selectAll().get(NoKeyDog.class).toString());

		SessionFactory.getSession().rollback();
	}

}
