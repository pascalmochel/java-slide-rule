package org.frijoles3.holder.impl;

import java.lang.reflect.Method;

import org.frijoles3.holder.AbstractHolder;

public class Singleton extends AbstractHolder {

	protected Object bean;
	protected volatile boolean initializated;
	protected Object beanMutex = new Object();

	public Singleton(final String alias, final Object factoryObject, final Object factoryProxy) {
		super(alias, factoryObject, factoryProxy);
	}

	@Override
	public Object getBean(final Method method, final Object[] extraParameters) {
		if (!initializated) {
			synchronized (beanMutex) {
				if (!initializated) {
					this.bean = buildInstance(method, extraParameters);
					initializated = true;
				}
			}
		}
		return this.bean;
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
		return getAlias() + "={singleton}[init:" + initializated + "]";
	}

}
