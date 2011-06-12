package org.frijoles3.test.errors;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Prototype;

public interface IErrorsFactory {

	Long getValue1(final IErrorsFactory f);

	@Scope(Prototype.class)
	Long getValue2(final IErrorsFactory f);

}
