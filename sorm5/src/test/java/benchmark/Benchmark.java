package benchmark;

import org.junit.Test;
import org.morm.test.EntityTest2;

import benchmark.hibernate.HibernateBenchmark;
import benchmark.sorm.SormBenchmark;

public class Benchmark {

	static {
		new EntityTest2();
	}
	int N = 100;

	@Test
	public void testname() throws Exception {
		final long t1 = testHibernate();
		final long t2 = testSorm();
		final long max = Math.max(t1, t2);
		System.out.println(100 * t1 / max + ":" + 100 * t2 / max);
	}

	public long testHibernate() throws Exception {

		final HibernateBenchmark b1 = new HibernateBenchmark();
		b1.beforeHibernateTest();
		final long l1 = System.currentTimeMillis();
		for (int i = 0; i < N; i++) {
			b1.testname();
		}

		return System.currentTimeMillis() - l1;
	}

	public long testSorm() throws Exception {

		final SormBenchmark b2 = new SormBenchmark();
		b2.before();
		final long l1 = System.currentTimeMillis();
		for (int i = 0; i < N; i++) {
			b2.testname();
		}
		final long l2 = System.currentTimeMillis() - l1;
		b2.after();

		return l2;
	}

}
