package org.frijoles3;

import java.awt.Color;

import org.frijoles3.anno.Scope;
import org.frijoles3.ents.Dog;
import org.frijoles3.ents.Person;
import org.frijoles3.holder.impl.Prototype;
import org.frijoles3.holder.impl.Singleton;
import org.frijoles3.holder.impl.Thread;

public interface ITestingFactory {

	@Scope(Prototype.class)
	Long getResult(final ITestingFactory f);

	@Scope(Prototype.class)
	Long getOperand1(final ITestingFactory f);

	@Scope(Prototype.class)
	Long getOperand2(final ITestingFactory f);

	@Scope(Prototype.class)
	Color getPrototypeBlueColor(final ITestingFactory f);

	@Scope(Singleton.class)
	Color getSingleRedColor(final ITestingFactory f);

	@Scope(Singleton.class)
	Dog getChucho(final ITestingFactory f);

	@Scope(Thread.class)
	Person getPerson(final ITestingFactory f);

}
