package org.frijoles3.test.nil;

import org.junit.Ignore;

@Ignore
public class NullFactory implements INullFactory {

	public Integer getNull(final INullFactory f) {
		return null;
	}

}
