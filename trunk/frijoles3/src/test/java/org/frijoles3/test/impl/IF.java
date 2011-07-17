package org.frijoles3.test.impl;

import org.frijoles3.anno.Scope;

import java.util.List;

public interface IF {

	@Scope
	List<Long> getList(IF self);
}
