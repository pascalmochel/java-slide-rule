package benchmark;

import org.junit.Test;
import org.morm.datasource.HsqldbDataSourceFactory;
import org.morm.session.SessionFactory;

import benchmark.hibernate.HibernateBenchmark;
import benchmark.sorm.SormBenchmark;

public class Benchmark {

	static {
		new SessionFactory().setDataSource(new HsqldbDataSourceFactory().getDataSource());
	}
	int N = 100;

	@Test
	public void testname() throws Exception {
		final long t1 = testHibernate();
		final long t2 = testSorm();
		final long max = Math.min(t1, t2);
		System.out.println(t1 + "ns:" + t2 + "ns");
		System.out.println(t1 / max + ":" + t2 / max);
	}

	public long testHibernate() throws Exception {

		final HibernateBenchmark b1 = new HibernateBenchmark();
		b1.beforeHibernateTest();
		final long l1 = System.nanoTime();
		for (int i = 0; i < N; i++) {
			b1.testname();
		}

		return System.nanoTime() - l1;
	}

	public long testSorm() throws Exception {

		final SormBenchmark b2 = new SormBenchmark();
		b2.before();
		final long l1 = System.nanoTime();
		for (int i = 0; i < N; i++) {
			b2.testname();
		}
		final long l2 = System.nanoTime() - l1;
		b2.after();

		return l2;
	}

}