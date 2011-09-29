package org.orm.session;

import org.junit.Test;

public class ThreadedNestedTransactionsTest {

	@Test
	public void threadsTest() {

		final int NUM_THREADS = 10;

		final ThreadRunner[] threads = new ThreadRunner[NUM_THREADS];

		for (int i = 0; i < NUM_THREADS; i++) {
			threads[i] = new ThreadRunner(String.valueOf(i));
		}
		for (int i = 0; i < NUM_THREADS; i++) {
			threads[i].run();
		}
	}

}

class ThreadRunner extends Thread {
	private final NestedTransactionsTest test = new NestedTransactionsTest();

	public ThreadRunner(final String str) {
		super(str);
	}

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			try {
				test.before();
				test.testMachine("{0+1{+2{+3}2}1+2]{2}");
				test.after();
				test.before();
				test.testMachine("{0+1{+2{+3]3+4]4+5}{0}");
				test.after();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}
}
