package org.frijoles3.test.impl;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Prototype;

import java.util.ArrayList;
import java.util.List;

public class F implements IF {

	@Scope(Prototype.class)
	public List<Long> getList(final IF self) {
		final List<Long> r = new ArrayList<Long>();
		r.add(1L);
		r.add(2L);
		r.add(3L);
		return r;
	}

}
