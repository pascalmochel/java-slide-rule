package org.orm.record.identity.impl.hsqldb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.orm.criteria.Criteria;
import org.orm.datasource.HsqldbDataSourceFactory;
import org.orm.mapper.DataMapper;
import org.orm.session.SessionFactory;
import org.orm.test.EntityTest2;

import static org.junit.Assert.*;

public class HsqldbIdentityTest {

	static {
		SessionFactory.setDataSource(new HsqldbDataSourceFactory().getDataSource());
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
	public void testname() throws Exception {
		SessionFactory.getSession().open();

		new IdentityDog(null, "din", 9).store();
		assertEquals("[[ID_DOG=100, NAME=din, AGE=9]]", Criteria.selectAll().list(IdentityDog.class)
				.toString());

		SessionFactory.getSession().rollback();
	}

}
