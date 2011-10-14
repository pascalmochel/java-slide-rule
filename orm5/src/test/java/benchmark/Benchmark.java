package benchmark;

import org.orm.datasource.HsqldbDataSourceFactory;
import org.orm.session.SessionFactory;

import benchmark.hibernate.HibernateBenchmark;
import benchmark.myorm.OrmBenchmark;

import java.util.ArrayList;
import java.util.List;

public class Benchmark {

	static {
		SessionFactory.configDataSource(new HsqldbDataSourceFactory().getDataSource());
	}

	protected int N = 50;

	public static void main(final String[] args) throws Exception {

		final List<String> r = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			final long t2 = new Benchmark().testOrm();
			final long t1 = new Benchmark().testHibernate();
			final long min = Math.min(t1, t2);
			System.out.println(t1 + "ns:" + t2 + "ns");
			System.out.println(100 * t1 / min + ":" + 100 * t2 / min);

			r.add(100 * t1 / min + ":" + 100 * t2 / min);
		}

		System.out.println(r);
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

	public long testOrm() throws Exception {

		final OrmBenchmark b2 = new OrmBenchmark();
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
