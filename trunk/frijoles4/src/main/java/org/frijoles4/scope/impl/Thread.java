package org.frijoles4.scope.impl;

import org.frijoles4.obtainer.IBeanObtainer;
import org.frijoles4.scope.ScopedBean;

public class Thread extends ScopedBean {

	protected ThreadLocal<Boolean> initializated = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		};
	};
	protected final ThreadLocal<Singleton> threadBean;

	public Thread(final String alias, final IBeanObtainer beanObtainer) {
		super(alias, beanObtainer);
		this.threadBean = new ThreadLocal<Singleton>() {
			@Override
			protected Singleton initialValue() {
				return new Singleton(alias, beanObtainer);
			}
		};
	}

	@Override
	public Object getBean(Object... args) {
		initializated.set(Boolean.TRUE);
		return threadBean.get().getBean(args);
	}

	@Override
	public String toString() {
		return alias + "={thread}";
	}

}
