package org.frijoles3.test.basic;

import java.awt.Color;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Prototype;
import org.frijoles3.holder.impl.Thread;
import org.frijoles3.test.ents.Dog;
import org.frijoles3.test.ents.Person;
import org.junit.Ignore;

@Ignore
public class TestingFactory implements ITestingFactory {

	@Scope(Prototype.class)
	public Long getResult(final ITestingFactory f) {
		return f.getOperand1(null);
	}

	@Scope(Prototype.class)
	public Long getOperand1(final ITestingFactory f) {
		return 5L * f.getOperand2(null);
	}

	@Scope(Prototype.class)
	public Long getOperand2(final ITestingFactory f) {
		return 3L;
	}

	@Scope(Prototype.class)
	public Color getPrototypeBlueColor(final ITestingFactory f) {
		return new Color(255);
	}

	@Scope
	public Color getSingleRedColor(final ITestingFactory f) {
		return new Color(0);
	}

	@Scope
	public Dog getChucho(final ITestingFactory f) {
		final Dog r = new Dog();
		r.setName("singleton-chucho");
		return r;
	}

	@Scope(Thread.class)
	public Person getPerson(final ITestingFactory f) {
		final Person r = new Person();
		r.setName("thread-mhc");
		r.setDog(f.getChucho(null));
		return r;
	}

}