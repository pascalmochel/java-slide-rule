package benchmark.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

import benchmark.hibernate.utils.HibernateSessionFactory;
import benchmark.hibernate.utils.PropertiesLoader;
import static org.junit.Assert.*;

public class HibernateBenchmark {

	@Before
	public void beforeHibernateTest() throws Exception {

		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}

		HibernateSessionFactory.setSessionFactory(//
				new Configuration()
				/**/.addProperties(PropertiesLoader.load("hibernate"))
				/**/.addResource("Votr.hbm.xml")
				/**/.buildSessionFactory() //
				);
	}

	@Test
	public void testname() throws Exception {

		final Transaction tx = HibernateSessionFactory.getSession().beginTransaction();
		tx.begin();

		final Session s = HibernateSessionFactory.getSession();
		try {
			HibernateDog d = new HibernateDog(null, "din", 9);
			s.save(d);

			d = (HibernateDog) s.load(HibernateDog.class, d.getIdDog());

			assertEquals("HibernateDog [idDog=*, name=din, age=9]", d.toString().replaceAll("idDog=\\d+",
					"idDog=*"));

			tx.commit();
		} catch (final Exception e) {
			tx.rollback();
			throw e;
		} finally {
			s.flush();
			s.close();
		}
		
	}

}
