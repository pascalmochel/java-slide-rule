package org.frijoles4.test.nil;

import org.frijoles4.FrijolesContext;
import org.frijoles4.anno.Scope;

public class NullFactory {

	@Scope
	public Integer getNull(final FrijolesContext ctx) {
		return null;
	}

}
