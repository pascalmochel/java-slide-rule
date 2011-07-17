package org.frijoles3.test.impl;

import org.frijoles3.anno.Scope;

import java.util.ArrayList;
import java.util.List;
import org.frijoles3.holder.impl.Thread;

public class F2 extends F implements IF {

	@Override
	@Scope(Thread.class)
	public List<Long> getList(final IF self) {
		final List<Long> r = new ArrayList<Long>();
		r.add(1L);
		r.add(2L);
		r.add(3L);
		return r;
	}

}
