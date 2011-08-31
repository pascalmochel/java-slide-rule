package org.morm.mapper;

import org.junit.Test;
import org.morm.datasource.HsqldbDataSourceFactory;
import org.morm.session.SessionFactory;

import static org.junit.Assert.*;

public class DataMapperTest {

	static {
		new SessionFactory().setDataSource(new HsqldbDataSourceFactory().getDataSource());
	}

	@Test
	public void testname() {
		{
			SessionFactory.getCurrentSession().beginTransaction();
			DataMapper.executeDdl("CREATE TABLE DOG (ID_DOG INTEGER, NAME VARCHAR(20))");

			new Dog(1, "chucho").insert();
			SessionFactory.getCurrentSession().commit();
		}
		{
			SessionFactory.getCurrentSession().beginTransaction();
			final Dog d = Dog.loadById(1);
			assertEquals("Dog:(1,chucho)", d.toString());

			assertEquals("[Dog:(1,chucho)]", Dog.loadAll().toString());

			d.setName("din");
			d.update();

			assertEquals("[Dog:(1,din)]", Dog.loadAll().toString());
			SessionFactory.getCurrentSession().commit();
		}
		{
			SessionFactory.getCurrentSession().beginTransaction();
			DataMapper.executeDdl("DROP TABLE DOG");
			SessionFactory.getCurrentSession().commit();
		}
	}

}
