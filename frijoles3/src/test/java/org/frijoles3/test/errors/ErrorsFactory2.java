package org.frijoles3.test.errors;

import org.junit.Ignore;

@Ignore
public class ErrorsFactory2 implements IErrorsFactory {

	public ErrorsFactory2(final int n) {
	}

	public Long getValue1(final IErrorsFactory f) {
		return 1L;
	}

}