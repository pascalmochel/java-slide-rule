package org.morm.record;

import org.junit.Test;
import org.morm.datasource.HsqldbDataSourceFactory;
import org.morm.mapper.DataMapper;
import org.morm.session.SessionFactory;
import static org.junit.Assert.*;

public class M {

	static {
		new SessionFactory().setDataSource(new HsqldbDataSourceFactory().getDataSource());
	}

	@Test
	public void testname() throws Exception {

		{
			SessionFactory.getCurrentSession().beginTransaction();
			DataMapper.executeDdl("CREATE TABLE RABBIT (ID_RABBIT INTEGER, NAME VARCHAR(20))");
		}
		{
			final Rabbit X = new Rabbit();

			new Rabbit(5, "din").insert();

			try {
				X.loadById(4);
				fail();
			} catch (final Exception e2) {
				// assertEquals("java.lang.RuntimeException: no row produced",
				// e2.getMessage());
			}

			final Rabbit rabbit = X.loadById(5);
			System.out.println(rabbit);

			rabbit.setName("jou");
			rabbit.update();

			System.out.println(X.loadById(5));
			System.out.println(X.loadAll());

			SessionFactory.getCurrentSession().rollback();
		}
		{
			SessionFactory.getCurrentSession().beginTransaction();
			DataMapper.executeDdl("DROP TABLE RABBIT");
			SessionFactory.getCurrentSession().commit();
		}
	}

}
