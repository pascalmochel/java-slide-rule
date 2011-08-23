package org.frijoles4.exception;

import org.frijoles4.FrijolesContext;

public class ExceptionFactory {

	public Integer age(FrijolesContext ctx) {
		throw new NullPointerException("jou");
	}

}
