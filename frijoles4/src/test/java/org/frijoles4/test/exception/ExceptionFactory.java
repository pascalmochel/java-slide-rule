package org.frijoles4.test.exception;

import org.frijoles4.FrijolesContext;

public class ExceptionFactory {

	public Integer age(FrijolesContext ctx) {
		throw new NullPointerException("jou");
	}

}
