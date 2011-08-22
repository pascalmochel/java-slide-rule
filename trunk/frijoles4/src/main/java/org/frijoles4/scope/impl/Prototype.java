package org.frijoles4.scope.impl;

import org.frijoles4.obtainer.IBeanObtainer;
import org.frijoles4.scope.ScopedBean;

public class Prototype extends ScopedBean {

	public Prototype(String alias, IBeanObtainer beanObtainer) {
		super(alias, beanObtainer);
	}

	@Override
	public Object getBean(Object... args) {
		return beanObtainer.obtain(args);
	}

	@Override
	public String toString() {
		return alias + "={prototype}";
	}

}
