package org.frijoles3.test.errors;

import org.junit.Ignore;

@Ignore
public class ErrorsFactory implements IErrorsFactory {

	public Long getValue1(final IErrorsFactory f) {
		return 1L;
	}

	public Long getValue2(final IErrorsFactory f) {
		throw new RuntimeException("jou");
	}

}