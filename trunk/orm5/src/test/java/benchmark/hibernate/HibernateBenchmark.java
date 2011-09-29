package benchmark.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import benchmark.hibernate.utils.HibernateSessionFactory;
import benchmark.hibernate.utils.PropertiesLoader;
import static org.junit.Assert.*;

public class HibernateBenchmark {

	public void beforeHibernateTest() throws Exception {

		Class.forName("org.hsqldb.jdbcDriver");

		HibernateSessionFactory.setSessionFactory(//
				new Configuration()
				/**/.addProperties(PropertiesLoader.load("hibernate"))
				/**/.addResource("Mappings.hbm.xml")
				/**/.buildSessionFactory() //
				);
	}

	public void testname() throws Exception {

		final HibernateDog d;
		{
			final Transaction tx = HibernateSessionFactory.getSession().beginTransaction();
			tx.begin();
			final Session s = HibernateSessionFactory.getSession();
			try {

				d = new HibernateDog(null, "din", 9);
				s.save(d);

				tx.commit();
			} catch (final Exception e) {
				tx.rollback();
				throw e;
			} finally {
				s.flush();
				s.close();
			}
		}
		{
			final Transaction tx = HibernateSessionFactory.getSession().beginTransaction();
			tx.begin();
			final Session s = HibernateSessionFactory.getSession();
			try {

				final HibernateDog d2 = (HibernateDog) s.load(HibernateDog.class, d.getIdDog());
				assertEquals("HibernateDog [idDog=*, name=din, age=9]", d2.toString().replaceAll(
						"idDog=\\d+", "idDog=*"));

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

}
