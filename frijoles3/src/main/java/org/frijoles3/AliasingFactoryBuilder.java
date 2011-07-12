//package org.frijoles3;
//
//import java.lang.reflect.Method;
//
//import org.frijoles3.anno.Scope;
//import org.frijoles3.exception.FrijolesException;
//import org.frijoles3.holder.AbstractHolder;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class AliasingFactoryBuilder extends FactoryBuilder implements FrijolesFactory {
//
//	protected final Map<String, Method> aliasesMap;
//	protected final Method getBeanMethod;
//
//	@SuppressWarnings("unchecked")
//	public static <T> T build(final Class<T> factoryClassToProx) {
//
//		if (factoryClassToProx.isInterface()) {
//			throw new FrijolesException("factory must be a class, not an interface; offending object is "
//					+ factoryClassToProx.toString());
//		}
//
//		final Object factoryObject = ProxyUtils.newInstanceOf(factoryClassToProx);
//		return (T) ProxyUtils.buildAliasedFactoryProxy(factoryObject, new AliasingFactoryBuilder(
//				factoryObject));
//	}
//
//	protected AliasingFactoryBuilder(final Object factoryObject) {
//		super(factoryObject);
//		this.aliasesMap = new HashMap<String, Method>();
//		try {
//			this.getBeanMethod = FrijolesFactory.class.getMethod(FrijolesFactory.GETTER_METHOD_NAME,
//					String.class);
//		} catch (final Exception e) {
//			throw new RuntimeException(e);
//		}
//		initializefactory();
//	}
//
//	protected void initializefactory() {
//
//		final List<Method> ms = new ArrayList<Method>();
//		for (final Class<?> i : factoryObject.getClass().getInterfaces()) {
//			for (final Method m : i.getMethods()) {
//				ms.add(m);
//			}
//		}
//		for (final Method m : ms) {
//
//			final Scope scope;
//			try {
//				scope = getScopeAnnotation(m);
//			} catch (final FrijolesException e) {
//				continue;
//			}
//
//			final String alias = scope.alias().length() == 0 ? m.getName() : scope.alias();
//			final AbstractHolder newAbstractHolder = AbstractHolder.buildHolder(scope.value(), alias,
//					factoryObject);
//
//			beansMap.put(m, newAbstractHolder);
//			aliasesMap.put(m.getName(), m);
//			if (scope.alias().length() > 0) {
//				aliasesMap.put(scope.alias(), m);
//			}
//		}
//
//	}
//
//	@Override
//	public Object invoke(final Object proxy, final Method method, final Object[] args) {
//
//		if (this.getBeanMethod.equals(method)) {
//			return getBean((String) args[0]);
//		}
//		return super.invoke(proxy, method, args);
//	}
//
//	@SuppressWarnings("unchecked")
//	public <T> T getBean(final String aliasName) {
//		final Method method = aliasesMap.get(aliasName);
//		if (method == null) {
//			throw new FrijolesException("alias not defined: " + aliasName + " in factory: "
//					+ factoryObject.getClass());
//		}
//		final AbstractHolder holder = beansMap.get(method);
//		if (holder == null) {
//			throw new FrijolesException("holder not found, for alias: " + aliasName + " in factory: "
//					+ factoryObject.getClass());
//		}
//		return (T) holder.getBean(method, new Object[] { factoryObject });
//	}
//
// }
