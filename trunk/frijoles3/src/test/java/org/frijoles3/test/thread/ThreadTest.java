package org.frijoles3.test.thread;

import org.frijoles3.FactoryBuilder;
import org.frijoles3.test.basic.ITestingFactory;
import org.frijoles3.test.basic.TestingFactory;
import org.frijoles3.test.ents.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ThreadTest {

	protected final int N_THREADS = 50;

	protected ITestingFactory ctx;
	protected List<Person> persons;

	@Before
	public void before() {
		this.ctx = FactoryBuilder.build(TestingFactory.class);
		this.persons = new ArrayList<Person>();
	}

	@Test
	public void testname() throws Exception {

		final Thread[] rs = new Thread[N_THREADS];
		for (int i = 0; i < N_THREADS; i++) {
			rs[i] = new TestingThread(ctx, persons, String.valueOf(i));
		}
		for (int i = 0; i < N_THREADS; i++) {
			rs[i].start();
		}

		boolean anyAlive;
		do {
			anyAlive = false;
			for (int i = 0; i < N_THREADS; i++) {
				if (rs[i].isAlive()) {
					anyAlive = true;
				}
			}
		} while (anyAlive);

		assertEquals("[getChucho={singleton}[init:true], getPerson={thread}[init:false]]", ctx.toString());
	}

	@After
	public void after() {

		// System.out.println(persons.toString());
		assertEquals(N_THREADS, persons.size());
	}

}

@Ignore
class TestingThread extends Thread {

	protected ITestingFactory ctx;
	protected List<Person> persons;

	public TestingThread(final ITestingFactory ctx, final List<Person> persons, final String name) {
		super(name);
		this.ctx = ctx;
		this.persons = persons;
	}

	private void delay() {
		yield();
	}

	@Override
	public void run() {

		Person p1 = null;
		Person p2 = null;
		for (int i = 0; i < 1000; i++) {
			p1 = ctx.getPerson(ctx);
			delay();
			p2 = ctx.getPerson(ctx);
			delay();
		}

		// System.out.println(getName());

		assertSame(p1, p2);
		assertFalse(persons.contains(p1));
		persons.add(p1);
	}
}
