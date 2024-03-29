package org.orm.record.identity.impl.hsqldb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mb.criteria.Criteria;
import org.mb.datasource.HsqldbDataSourceFactory;
import org.mb.mapper.DataMapper;
import org.mb.session.SessionFactory;
import org.orm.test.EntityTest2;

import static org.junit.Assert.*;

public class SequenceTest {

	static {
		SessionFactory.configDataSource(new HsqldbDataSourceFactory().getDataSource());
		new EntityTest2();
	}

	@Before
	public void before() {
		SessionFactory.getSession().open();

		DataMapper.executeDDLIgnoringErrors("DROP SEQUENCE DOG_SEQUENCE");
		DataMapper.executeDDLIgnoringErrors("DROP TABLE DOG");

		DataMapper.executeDDL("CREATE SEQUENCE DOG_SEQUENCE AS INTEGER START WITH 1 INCREMENT BY 1");
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
		DataMapper.executeDDL("DROP SEQUENCE DOG_SEQUENCE");
		DataMapper.executeDDL("DROP TABLE DOG");
		SessionFactory.getSession().commit();
	}

	@Test
	public void testname() throws Exception {
		SessionFactory.getSession().open();

		new SequenceDog(null, "din", 9).store();
		assertEquals("[[ID_DOG=1, NAME=din, AGE=9]]", Criteria.selectAll().list(SequenceDog.class).toString());

		SessionFactory.getSession().rollback();
	}

}
