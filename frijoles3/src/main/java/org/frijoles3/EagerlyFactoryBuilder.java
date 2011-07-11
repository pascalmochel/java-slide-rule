package org.frijoles3;

import java.lang.reflect.Method;

import org.frijoles3.anno.Scope;
import org.frijoles3.exception.FrijolesException;
import org.frijoles3.holder.AbstractHolder;

import java.util.ArrayList;
import java.util.List;

public class EagerlyFactoryBuilder extends FactoryBuilder {

	// protected final Map<String, AbstractHolder> aliasesMap;

	@SuppressWarnings("unchecked")
	public static <T> T build(final Class<T> factoryClassToProx) {

		if (factoryClassToProx.isInterface()) {
			throw new FrijolesException("factory must be a class, not an interface; offending object is "
					+ factoryClassToProx.toString());
		}

		final Object factoryObject = ProxyUtils.newInstanceOf(factoryClassToProx);
		return (T) ProxyUtils.buildProxy(factoryObject, new EagerlyFactoryBuilder(factoryObject));
	}

	protected EagerlyFactoryBuilder(final Object factoryObject) {
		super(factoryObject);
		// this.aliasesMap = new HashMap<String, AbstractHolder>();
		initializefactory();
	}

	protected void initializefactory() {

		final List<Method> ms = new ArrayList<Method>();
		for (final Class<?> i : factoryObject.getClass().getInterfaces()) {
			for (final Method m : i.getMethods()) {
				ms.add(m);
			}
		}
		for (final Method m : ms) {

			final Scope scope;
			try {
				scope = getScopeAnnotation(m);
			} catch (final FrijolesException e) {
				continue;
			}

			final boolean aliased = scope.alias().length() == 0;

			String alias;
			if (aliased) {
				alias = m.getName();
			} else {
				alias = m.getName() + '/' + scope.alias();
			}
			final AbstractHolder newAbstractHolder = AbstractHolder.buildHolder(scope.value(), alias,
					factoryObject);

			beansMap.put(m, newAbstractHolder);
			// if (aliased) {
			// aliasesMap.put(alias, newAbstractHolder);
			// }
		}
	}

}