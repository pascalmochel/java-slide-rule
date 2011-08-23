package org.frijoles4.exception;

import org.frijoles4.FrijolesContext;
import org.frijoles4.anno.Scope;

public class ExceptionFactory {

	@Scope
	public Integer age(final FrijolesContext ctx) {
		throw new NullPointerException("jou");
	}

}
