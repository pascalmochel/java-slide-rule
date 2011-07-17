//package org.frijoles3;
//
//import java.lang.reflect.Method;
//
//import org.frijoles3.exception.FrijolesException;
//import org.frijoles3.holder.AbstractHolder;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
//public class Aliasing2FactoryBuilder extends EagerlyFactoryBuilder implements FrijolesFactory {
//
//	protected final Map<String, Method> aliasesMap;
//	protected final Method getBeanMethod;
//
//	public static FrijolesFactory build(final Class<?> factoryClassToProx) {
//
//		try {
//			if (factoryClassToProx.isInterface()) {
//				throw new FrijolesException("factory must be a class, not an interface; offending object is "
//						+ factoryClassToProx.toString());
//			}
//
//			final Object factoryObject = ProxyUtils.newInstanceOf(factoryClassToProx);
//			return (FrijolesFactory) ProxyUtils.buildAliasedFactoryProxy(factoryObject,
//					new Aliasing2FactoryBuilder(factoryObject));
//		} catch (final Exception e) {
//			throw new FrijolesException("error building factory: " + factoryClassToProx.getSimpleName(), e);
//		}
//	}
//
//	protected Aliasing2FactoryBuilder(final Object factoryObject) {
//		super(factoryObject);
//		this.aliasesMap = new HashMap<String, Method>();
//		try {
//			this.getBeanMethod = FrijolesFactory.class.getMethod(FrijolesFactory.GETTER_METHOD_NAME,
//					String.class, Object[].class);
//		} catch (final Exception e) {
//			throw new RuntimeException(e);
//		}
//
//		initializefactory();
//		initializeAliases();
//	}
//
//	protected final void initializeAliases() {
//
//		for (final Entry<Method, AbstractHolder> e : super.beansMap.entrySet()) {
//			aliasesMap.put(e.getKey().getName(), e.getKey());
//		}
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
//	public <T> T getBean(final String alias, final Object... params) {
//		final Method m = aliasesMap.get(alias);
//		final AbstractHolder holder = super.beansMap.get(m);
//		
//		
//		final Object[] callArguments = setFirst(proxy, params);
//		return (T) holder.getBean(m, params);
//	}
//
// }
