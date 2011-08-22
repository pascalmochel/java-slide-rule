package org.frijoles4;

import java.lang.reflect.Method;

import org.frijoles4.aliasp.AliasDecameler;
import org.frijoles4.aliasp.IAliasProcessor;
import org.frijoles4.anno.Alias;
import org.frijoles4.anno.Scope;
import org.frijoles4.exception.AliasNotDefinedException;
import org.frijoles4.exception.FrijolesException;
import org.frijoles4.obtainer.BeanObtainer;
import org.frijoles4.scope.ScopedBean;
import org.frijoles4.scope.impl.Singleton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FrijolesContext {

	private static final Class<? extends ScopedBean> DEFAULT_SCOPE = Singleton.class;
	private static final IAliasProcessor DEFAULT_ALIAS_PROCESSOR = new AliasDecameler();

	protected final Map<String, ScopedBean> beansMap;
	protected final IAliasProcessor aliasProcessor;

	protected final Set<Method> objectsMethods;

	public static FrijolesContext build(final Class<?>... factoryClasses) {
		try {
			return new FrijolesContext(DEFAULT_ALIAS_PROCESSOR).initialize(factoryClasses);
		} catch (final Exception e) {
			throw new FrijolesException("configuring factory context: " + Arrays.toString(factoryClasses), e);
		}
	}

	public static FrijolesContext build(final IAliasProcessor aliasProcessor,
			final Class<?>... factoryClasses) {
		try {
			return new FrijolesContext(aliasProcessor).initialize(factoryClasses);
		} catch (final Exception e) {
			throw new FrijolesException("configuring factory context: " + Arrays.toString(factoryClasses), e);
		}
	}

	protected FrijolesContext() {
		super();
		this.beansMap = new HashMap<String, ScopedBean>();
		this.aliasProcessor = null;
		this.objectsMethods = Utils.getObjectMethods();
	}

	protected FrijolesContext(final IAliasProcessor aliasProcessor) {
		super();
		this.beansMap = new HashMap<String, ScopedBean>();
		this.aliasProcessor = aliasProcessor;
		this.objectsMethods = Utils.getObjectMethods();
	}

	protected FrijolesContext initialize(final Class<?>... factoryClasses) {
		for (final Class<?> factoryClass : factoryClasses) {
			final Object factory = Utils.newInstanceOf(factoryClass);
			for (final Method method : factoryClass.getMethods()) {

				try {

					// XXX descartar mètodes de Object
					if (objectsMethods.contains(method)) {
						continue;
					}

					final String alias = getAlias(method);
					// XXX i si aquest alias ja existeix?
					if (beansMap.containsKey(alias)) {
						throw new FrijolesException("alias " + alias
								+ " yet defined; configuring factory method: " + method.toString());
					}

					// XXX verificar que el primer arg del mètode sigui de tipo
					// FrijolesContext
					if (method.getParameterTypes().length == 0
							|| !method.getParameterTypes()[0].isAssignableFrom(FrijolesContext.class)) {
						throw new FrijolesException("first parameter must be of type "
								+ FrijolesContext.class.getName() + "; in factory method: "
								+ method.toString());
					}

					final ScopedBean scopedBean = ScopedBean.build(alias, getScopeType(method),
							new BeanObtainer(factory, method));

					beansMap.put(alias, scopedBean);

				} catch (final Exception e) {
					throw new FrijolesException("error defining factory method: " + method.toString(), e);
				}
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
