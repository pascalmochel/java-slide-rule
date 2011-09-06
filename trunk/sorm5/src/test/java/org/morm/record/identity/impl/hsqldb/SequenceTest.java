package org.morm.record.identity.impl.hsqldb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.morm.datasource.HsqldbDataSourceFactory;
import org.morm.mapper.DataMapper;
import org.morm.session.SessionFactory;
import org.morm.test.EntityTest2;

import static org.junit.Assert.*;

public class SequenceTest {

	static {
		new SessionFactory().setDataSource(new HsqldbDataSourceFactory().getDataSource());
		new EntityTest2();
	}

	@Before
	public void before() {
		SessionFactory.getCurrentSession().beginTransaction();

		DataMapper.executeDDLIgnoringErrors("DROP SEQUENCE DOG_SEQUENCE");
		DataMapper.executeDDLIgnoringErrors("DROP TABLE DOG");

		DataMapper.executeDDL("CREATE SEQUENCE DOG_SEQUENCE AS INTEGER START WITH 1 INCREMENT BY 1");
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
		DataMapper.executeDDL("DROP SEQUENCE DOG_SEQUENCE");
		DataMapper.executeDDL("DROP TABLE DOG");
		SessionFactory.getCurrentSession().commit();
	}

	@Test
	public void testname() throws Exception {
		SessionFactory.getCurrentSession().beginTransaction();

		new SequenceDog(null, "din", 9).store();
		assertEquals("[[ID_DOG=1, NAME=din, AGE=9]]", new SequenceDog().loadAll().toString());

		SessionFactory.getCurrentSession().rollback();
	}

}
