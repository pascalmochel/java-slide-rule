//package org.mb;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.mb.criteria.Criteria;
//import org.mb.datasource.HsqldbDataSourceFactory;
//import org.mb.mapper.DataMapper;
//import org.mb.session.SessionFactory;
//import org.mb.test.EntityTest2;
//
//import static org.junit.Assert.*;
//
//public class ConfigTest {
//
//	static {
//		SessionFactory.setDataSource(new HsqldbDataSourceFactory().getDataSource());
//		new EntityTest2();
//	}
//
//	@Before
//	public void before() {
//		SessionFactory.getSession().open();
//
//		DataMapper.executeDDLIgnoringErrors("DROP TABLE PIZZA");
//
//		DataMapper.executeDDL(
//		/**/"CREATE TABLE PIZZA (" +
//		/**/"ID_PIZZA INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY," +
//		/**/"NAME VARCHAR(20)," +
//		/**/"PRICE INTEGER)"
//		/**/);
//
//		SessionFactory.getSession().commit();
//	}
//
//	@After
//	public void after() {
//		try {
//			SessionFactory.getSession().open();
//			DataMapper.executeDDL("DROP TABLE PIZZA");
//			SessionFactory.getSession().commit();
//		} catch (Exception e) {
//		}
//	}
//
//	@Test
//	public void testname() throws Exception {
//		SessionFactory.getSession().open();
//
//		new Pizza(null, "din", 9).store();
//		assertEquals("[[ID_PIZZA=100, NAME=din, PRICE=9]]", Criteria.selectAll().list(Pizza.class).toString());
//
//		SessionFactory.getSession().rollback();
//	}
//
//}
