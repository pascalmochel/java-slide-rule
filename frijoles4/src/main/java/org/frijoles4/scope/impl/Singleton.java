package org.frijoles4.scope.impl;

import org.frijoles4.obtainer.IBeanObtainer;
import org.frijoles4.scope.ScopedBean;

public class Singleton extends ScopedBean {

	protected Object bean;
	protected volatile boolean initializated; // NOPMD
	protected Object beanMutex = new Object();

	public Singleton(String alias, IBeanObtainer beanObtainer) {
		super(alias, beanObtainer);
	}

	@Override
	public Object getBean(Object... args) {
		if (!initializated) {
			synchronized (beanMutex) {
				if (!initializated) {
					this.bean = beanObtainer.obtain(args);
					initializated = true;
				}
			}
		}
		return this.bean;
	}

	@Override
	public String toString() {
		return alias + "={singleton}";
	}

}
