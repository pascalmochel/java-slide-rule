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

	// public ThreadScopedBean(final String alias, final Object factory, final
	// Method method) {
	// super(alias, factory, method);
	// this.threadBean = new ThreadLocal<SingletonScopedBean>() {
	// @Override
	// protected SingletonScopedBean initialValue() {
	// return new SingletonScopedBean(alias, factory, method);
	// }
	// };
	// }

	public Thread(final Object factoryObject, final Object factoryProxy) {
		super(factoryObject, factoryProxy);
		this.threadBean = new ThreadLocal<Singleton>() {
			@Override
			protected Singleton initialValue() {
				return new Singleton(factoryObject, factoryProxy);
			}
		};
	}

	@Override
	public Object getBean(final Method method) {
		// if (!initializated) {
		// synchronized (beanMutex) {
		// if (!initializated) {
		// this.bean = buildInstance(method);
		// initializated = true;
		// }
		// }
		// }
		// return this.bean;
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
	// @Override
	// public String toString() {
	// return alias + "{" + getScopedBeanNature() + "}" + "[init=" +
	// isInitializated() + "]";
	// }

}
