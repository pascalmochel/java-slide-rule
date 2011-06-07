package org.frijoles3;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Prototype;

public interface ITestingFactory {

	@Scope(Prototype.class)
	Long getResult(final ITestingFactory f);

	@Scope(Prototype.class)
	Long getOperand1(final ITestingFactory f);

	@Scope(Prototype.class)
	Long getOperand2(final ITestingFactory f);
}
