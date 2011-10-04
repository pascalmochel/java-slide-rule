package org.orm.a;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.orm.datasource.HsqldbDataSourceFactory;
import org.orm.mapper.DataMapper;
import org.orm.record.Entity;
import org.orm.session.SessionFactory;
import org.orm.test.EntityTest2;

import static org.junit.Assert.*;
import static org.orm.criteria.Criteria.*;
import static org.orm.criteria.restriction.Restriction.*;

public class TestA {

	static {
		new EntityTest2();
		SessionFactory.setDataSource(new HsqldbDataSourceFactory().getDataSource());
	}

	@Test
	public void test() {

		SessionFactory.getSession().open();

		try {
			DataMapper.executeDDL("INSERT INTO CITY (ID_CITY,NAME) VALUES (100,'SBD')");
			Entity.sqlStatement("INSERT INTO CITY (ID_CITY,NAME) VALUES (?,?)", 101, "TRS");

			City sbd = select().where(eq(City.name, "SBD")).getUnique(City.class);
			assertEquals("[ID_CITY=100, NAME=SBD]", sbd.toString());

			sbd.setName("Sabadell");
			sbd.store();

			sbd = Entity.loadById(City.class, sbd.getId());
			assertEquals("[ID_CITY=100, NAME=Sabadell]", sbd.toString());

			assertEquals("[[ID_CITY=100, NAME=Sabadell], [ID_CITY=101, NAME=TRS]]",
			/**/Entity.loadAll(City.class).toString());

			// final long no = Entity.count(City.class, all());
			// assertEquals(2, no);

		} finally {
			SessionFactory.getSession().rollback();
		}
	}

	@Before
	public void before() {
		SessionFactory.getSession().open();

		DataMapper.executeDDLIgnoringErrors("DROP TABLE CITY");

		DataMapper.executeDDL(
		/**/"CREATE TABLE CITY (" +
		/**/"ID_CITY INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY," +
		/**/"NAME VARCHAR(20)" +
		/**/")"
		/**/);
		SessionFactory.getSession().commit();
	}

	@After
	public void after() {
		SessionFactory.getSession().open();
		DataMapper.executeDDL("DROP TABLE CITY");
		SessionFactory.getSession().commit();
	}

}
