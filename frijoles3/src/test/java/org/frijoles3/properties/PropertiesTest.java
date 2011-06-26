package org.frijoles3.properties;

import javax.sql.DataSource;

import org.frijoles3.FactoryBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class PropertiesTest {

	@Test
	public void testname() throws Exception {
		final IBusiness f = FactoryBuilder.build(Business.class);

		final DataSource ds = f.getDataSource(null);

		assertEquals("jdbc:hsqldb:file:/home/mhoms/hsql-db", ds.getConnection().getMetaData().getURL());

		// final Session session = f.getSessionFactory(null).openSession();
	}

}
