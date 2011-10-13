//package org.orm.collection;
//
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
//public class M<K, V> implements Map<K, V>, Iterable<V> {
//
//	protected final H<V> h;
//
//	@SuppressWarnings("unchecked")
//	public M(final Class<? extends V> elementClass) {
//		this.h = new H<V>((Class<V>) elementClass, 6, 3);
//	}
//
//	@Deprecated
//	@Override
//	public void clear() {
//		throw new RuntimeException("rrr");
//	}
//
//	@Override
//	public boolean containsKey(final Object key) {
//		return h.get(key.hashCode()) != null;
//	}
//
//	@Deprecated
//	@Override
//	public boolean containsValue(final Object value) {
//		throw new RuntimeException("rrr");
//	}
//
//	@Deprecated
//	@Override
//	public Set<java.util.Map.Entry<K, V>> entrySet() {
//		throw new RuntimeException("rrr");
//	}
//
//	@Override
//	public V get(final Object key) {
//		return h.get(key.hashCode());
//	}
//
//	@Override
//	public boolean isEmpty() {
//		return h.getSize() == 0;
//	}
//
//	@Deprecated
//	@Override
//	public Set<K> keySet() {
//		throw new RuntimeException("rrr");
//	}
//
//	@Override
//	public V put(final K key, final V value) {
//		h.put(key.hashCode(), value);
//		return value;
//	}
//
//	@Deprecated
//	@Override
//	public void putAll(final Map<? extends K, ? extends V> m) {
//		throw new RuntimeException("rrr");
//	}
//
//	@Deprecated
//	@Override
//	public V remove(final Object key) {
//		throw new RuntimeException("rrr");
//	}
//
//	@Override
//	public int size() {
//		return h.getSize();
//	}
//
//	@Deprecated
//	@Override
//	public Collection<V> values() {
//		throw new RuntimeException("rrr");
//	}
//
//	@Override
//	public Iterator<V> iterator() {
//		return h.iterator();
//	}
//
// }
