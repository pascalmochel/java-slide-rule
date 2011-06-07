package org.frijoles3.holder.impl;

import java.lang.reflect.Method;

import org.frijoles3.holder.Holder;

public class Thread extends Holder {

	protected ThreadLocal<Boolean> initializated = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		};
	};
	protected final ThreadLocal<Singleton> threadBean;

	public Thread(final String alias, final Object factoryObject, final Object factoryProxy) {
		super(alias, factoryObject, factoryProxy);
		this.threadBean = new ThreadLocal<Singleton>() {
			@Override
			protected Singleton initialValue() {
				return new Singleton(alias, factoryObject, factoryProxy);
			}
		};
	}

	@Override
	public Object getBean(final Method method) {

		initializated.set(Boolean.TRUE);
		return threadBean.get().getBean(method);
	}

	// @Override
	// public boolean isInitializated() {
	// return initializated;
	// }
	//
	// @Override
	// public String getScopedBeanNature() {
	// return "singleton";
	// }
	//
	@Override
	public String toString() {
		return getAlias() + "{thread}[init=" + initializated.get() + "]";
	}

}
