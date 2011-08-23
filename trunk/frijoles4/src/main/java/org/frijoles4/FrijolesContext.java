package org.frijoles4;

import java.lang.reflect.Method;

import org.frijoles4.aliasp.AliasDecameler;
import org.frijoles4.aliasp.IAliasProcessor;
import org.frijoles4.anno.Alias;
import org.frijoles4.anno.Scope;
import org.frijoles4.exception.AliasNotDefinedException;
import org.frijoles4.exception.FrijolesException;
import org.frijoles4.exception.ThrowableRenderer;
import org.frijoles4.obtainer.BeanObtainer;
import org.frijoles4.scope.ScopedBean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

//TODO serialitzable?
public class FrijolesContext {

	private static final IAliasProcessor DEFAULT_ALIAS_PROCESSOR = new AliasDecameler();

	protected final Map<String, ScopedBean> beansMap;
	protected final IAliasProcessor aliasProcessor;

	public static FrijolesContext build(final IAliasProcessor aliasProcessor,
			final Class<?>... factoryClasses) {
		try {
			return new FrijolesContext(aliasProcessor).initialize(factoryClasses);
		} catch (final Exception e) {
			throw new FrijolesException("configuring context with factories: "
					+ Arrays.toString(factoryClasses), e);
		}
	}

	public static FrijolesContext build(final Class<?>... factoryClasses) {
		return build(DEFAULT_ALIAS_PROCESSOR, factoryClasses);
	}

	protected FrijolesContext(final IAliasProcessor aliasProcessor) {
		super();
		this.beansMap = new HashMap<String, ScopedBean>();
		this.aliasProcessor = aliasProcessor;
	}

	protected FrijolesContext() {
		this(null);
	}

	protected FrijolesContext initialize(final Class<?>... factoryClasses) {
		for (final Class<?> factoryClass : factoryClasses) {
			final Object factory = Utils.newInstanceOf(factoryClass);
			for (final Method method : factoryClass.getMethods()) {

				try {

					final Scope anno = method.getAnnotation(Scope.class);
					if (anno == null) {
						continue;
					}

					final String alias = getAlias(method);
					// XXX i si aquest alias ja existeix?
					if (beansMap.containsKey(alias)) {
						throw new FrijolesException("alias " + alias
								+ " yet defined; configuring context method: " + method.toString());
					}

					// XXX verificar que el primer arg del mètode sigui de tipo
					// FrijolesContext
					if (method.getParameterTypes().length == 0
							|| !method.getParameterTypes()[0].isAssignableFrom(FrijolesContext.class)) {
						throw new FrijolesException("first parameter must be of type "
								+ FrijolesContext.class.getName() + "; in context method: "
								+ method.toString());
					}
					if (method.getParameterTypes().length > 2) {
						throw new FrijolesException("valid method factory must have a "
								+ getClass().getName()
								+ " as argument, and optionally, depending of the kind of scope, "
								+ "an HttpSession/ServletRequest as a second argument.");
					}

					final ScopedBean scopedBean = ScopedBean.build(alias, anno.value(), new BeanObtainer(
							factory, method));

					beansMap.put(alias, scopedBean);

				} catch (final Exception e) {
					throw new FrijolesException("error defining context method: "
							+ ThrowableRenderer.renderFactoryMethodInfo(method), e);
				}
			}
		}
		return this;
	}

	protected String getAlias(final Method method) {
		final String alias;
		final Alias anno = method.getAnnotation(Alias.class);
		if (anno == null) {
			alias = aliasProcessor.processAlias(method.getName());
		} else {
			alias = anno.value();
		}
		return alias;
	}

	public Object getBean(final String alias) {
		final ScopedBean scopedBean = beansMap.get(alias);
		if (scopedBean == null) {
			final String[] aliases = beansMap.keySet().toArray(new String[beansMap.size()]);
			throw new AliasNotDefinedException(alias, aliases);
		}
		// final Object[] consedArgs = Utils.cons(this, args);
		final Object[] consedArgs = new Object[] { this };
		return scopedBean.getBean(consedArgs);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(final Class<T> resultingType, final String alias) {
		return (T) getBean(alias);
	}

	public Object getBean(final String alias, final Object optionalHttpArg) {
		final ScopedBean scopedBean = beansMap.get(alias);
		if (scopedBean == null) {
			final String[] aliases = beansMap.keySet().toArray(new String[beansMap.size()]);
			throw new AliasNotDefinedException(alias, aliases);
		}
		final Object[] consedArgs = Utils.cons(this, new Object[] { optionalHttpArg });
		return scopedBean.getBean(consedArgs);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(final Class<T> resultingType, final String alias, final Object optionalHttpArg) {
		return (T) getBean(alias, optionalHttpArg);
	}

	@Override
	public String toString() {
		final Set<String> m = new TreeSet<String>();
		for (final ScopedBean h : beansMap.values()) {
			m.add(h.toString());
		}
		return m.toString();
	}

}
