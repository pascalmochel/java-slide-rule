package org.frijoles4;

import java.lang.reflect.Method;

import org.frijoles4.aliasp.AliasDecameler;
import org.frijoles4.aliasp.IAliasProcessor;
import org.frijoles4.anno.Alias;
import org.frijoles4.anno.Scope;
import org.frijoles4.exception.AliasNotDefinedException;
import org.frijoles4.obtainer.BeanObtainer;
import org.frijoles4.scope.ScopedBean;
import org.frijoles4.scope.impl.Singleton;

import java.util.HashMap;
import java.util.Map;

public class FrijolesContext {

	private static final Class<? extends ScopedBean> DEFAULT_SCOPE = Singleton.class;
	private static final IAliasProcessor DEFAULT_ALIAS_PROCESSOR = new AliasDecameler();

	protected final Map<String, ScopedBean> beansMap;
	protected final IAliasProcessor aliasProcessor;

	public static FrijolesContext build(final Class<?>... factoryClasses) {
		return new FrijolesContext(DEFAULT_ALIAS_PROCESSOR).initialize(factoryClasses);
	}

	public static FrijolesContext build(final IAliasProcessor aliasProcessor,
			final Class<?>... factoryClasses) {
		return new FrijolesContext(aliasProcessor).initialize(factoryClasses);
	}

	protected FrijolesContext() {
		super();
		this.beansMap = new HashMap<String, ScopedBean>();
		this.aliasProcessor = null;
	}

	protected FrijolesContext(final IAliasProcessor aliasProcessor) {
		super();
		this.beansMap = new HashMap<String, ScopedBean>();
		this.aliasProcessor = aliasProcessor;
	}

	protected FrijolesContext initialize(final Class<?>... factoryClasses) {
		for (final Class<?> factoryClass : factoryClasses) {
			final Object factory = Utils.newInstanceOf(factoryClass);
			for (final Method method : factoryClass.getMethods()) {
				final String alias = getAlias(method);
				final ScopedBean scopedBean = ScopedBean.build(alias, getScopeType(method), new BeanObtainer(
						factory, method));
				// TODO descartar mètodes de Object
				// TODO i si aquest alias ja existia?
				// TODO verificar que el primer arg del mètode sigui de tipo
				// FrijolesContext
				beansMap.put(alias, scopedBean);
			}
		}
		return this;
	}

	protected String getAlias(final Method method) {
		final Alias anno = method.getAnnotation(Alias.class);
		final String alias;
		if (anno == null) {
			alias = aliasProcessor.processAlias(method.getName());
		} else {
			alias = anno.value();
		}
		return alias;
	}

	protected Class<? extends ScopedBean> getScopeType(final Method method) {
		final Scope anno = method.getAnnotation(Scope.class);
		return anno == null ? DEFAULT_SCOPE : anno.value();
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(final String alias, final Object... args) {
		final ScopedBean scopedBean = beansMap.get(alias);
		if (scopedBean == null) {
			final String[] aliases = beansMap.keySet().toArray(new String[beansMap.size()]);
			throw new AliasNotDefinedException(alias, aliases);
		}
		final Object[] consedArgs = Utils.cons(this, args);
		return (T) scopedBean.getBean(consedArgs);
	}

	@Override
	public String toString() {
		return beansMap.values().toString();
	}

}
