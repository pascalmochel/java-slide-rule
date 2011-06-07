package org.frijoles3;

import java.awt.Color;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Prototype;
import org.frijoles3.holder.impl.Singleton;

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
}
