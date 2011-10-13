//package org.orm.collection;
//
//import java.util.Collection;
//import java.util.Map;
//import java.util.Set;
//
//public class Mape<T> implements Map<Integer, T> {
//
//	protected final HasheMap<T> h;
//
//	public Mape(final Class<T> elementClass, final int capacity) {
//		this.h = new HasheMap<T>(elementClass, capacity);
//	}
//
//	@Override
//	public void clear() {
//		this.h.clear();
//	}
//
//	@Override
//	public boolean containsKey(final Object key) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean containsValue(final Object value) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public Set<java.util.Map.Entry<Integer, T>> entrySet() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public T get(final Object key) {
//		return this.h.get(key.hashCode());
//	}
//
//	@Override
//	public boolean isEmpty() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public Set<Integer> keySet() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public T put(final Integer key, final T value) {
//		this.h.put(key.hashCode(), value);
//		return value;
//	}
//
//	@Override
//	public void putAll(final Map<? extends Integer, ? extends T> m) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public T remove(final Object key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int size() {
//		return this.h.getSize();
//	}
//
//	@Override
//	public Collection<T> values() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
// }
