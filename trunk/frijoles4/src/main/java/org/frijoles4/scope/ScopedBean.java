package org.frijoles4.scope;

import java.lang.reflect.Constructor;

import org.frijoles4.exception.FrijolesException;
import org.frijoles4.obtainer.IBeanObtainer;

public abstract class ScopedBean { // NOPMD

	protected final String alias;
	protected final IBeanObtainer beanObtainer;

	public ScopedBean(final String alias, final IBeanObtainer beanObtainer) {
		super();
		this.alias = alias;
		this.beanObtainer = beanObtainer;
	}

	public abstract Object getBean(Object... args);

	public static <T extends ScopedBean> T build(final String alias, final Class<T> claz,
			final IBeanObtainer beanObtainer) {
		Constructor<T> ctor;
		try {
			ctor = claz.getConstructor(String.class, IBeanObtainer.class);
			return ctor.newInstance(alias, beanObtainer);
		} catch (final Exception e) {
			throw new FrijolesException("internal failure; " + ScopedBean.class.getName() + " has changed?",
					e);
		}
	}

}
