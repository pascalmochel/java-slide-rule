package org.frijoles3.holder.impl;

import java.lang.reflect.Method;

import org.frijoles3.holder.Holder;

public class Prototype extends Holder {

	public Prototype(final Object factoryObject, final Object factoryProxy) {
		super(factoryObject, factoryProxy);
	}

	@Override
	public Object getBean(final Method method) {
		try {
			System.out.println("deholding: " + method.getName());
			return method.invoke(factoryObject, factoryProxy);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}