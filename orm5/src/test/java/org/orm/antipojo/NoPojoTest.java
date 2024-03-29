package org.orm.antipojo;

import static org.junit.Assert.assertEquals;
import static org.mb.criteria.Criteria.*;
import static org.mb.criteria.restriction.Restriction.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mb.criteria.Criteria;
import org.mb.datasource.HsqldbDataSourceFactory;
import org.mb.mapper.DataMapper;
import org.mb.record.field.regular.primitive.FLong;
import org.mb.session.SessionFactory;
import org.orm.test.EntityTest2;

public class NoPojoTest {

	static {
		new EntityTest2();
		SessionFactory.configDataSource(new HsqldbDataSourceFactory().getDataSource());
	}

	@Test
	public void test() {

		SessionFactory.getSession().open();

		try {
			DataMapper.executeDDL("INSERT INTO CITY (ID_CITY,NAME) VALUES (100,'SBD')");
			DataMapper.sqlStatement("INSERT INTO CITY (ID_CITY,NAME) VALUES (?,?)", 101, "TRS");

			City sbd = selectBy(eq(City.name, "SBD")).uniqueResult(City.class);
			assertEquals("[ID_CITY=100, NAME=SBD]", sbd.toString());

			sbd.set(City.name, "Sabadell");
			sbd.store();

			sbd = Criteria.selectById(City.class, sbd.get(City.id));
			assertEquals("[ID_CITY=100, NAME=Sabadell]", sbd.toString());

			assertEquals("[[ID_CITY=100, NAME=Sabadell], [ID_CITY=101, NAME=TRS]]",
			/**/Criteria.selectAll().list(City.class).toString());

			final long count = query("SELECT COUNT(*) AS VALUE FROM CITY").getColumnValue(new FLong("VALUE"));
			assertEquals(2L, count);

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
		/**/"   ID_CITY INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY," +
		/**/"   NAME VARCHAR(20)" +
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
