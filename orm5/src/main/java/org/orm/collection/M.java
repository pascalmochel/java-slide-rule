//package org.orm.collection;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//public class M<K, V> implements Map<K, V>, Iterable<V> {
//
//	protected final H<V> h;
//
//	public M(final Class<V> elementClass) {
//		this.h = new H<V>(elementClass, 6, 3);
//	}
//
//	@Deprecated
//	public void clear() {
//		throw new RuntimeException("rrr");
//	}
//
//	public boolean containsKey(final Object key) {
//		return h.get(key.hashCode()) != null;
//	}
//
//	@Deprecated
//	public boolean containsValue(final Object value) {
//		throw new RuntimeException("rrr");
//	}
//
//	@Deprecated
//	public Set<java.util.Map.Entry<K, V>> entrySet() {
//		throw new RuntimeException("rrr");
//	}
//
//	public V get(final Object key) {
//		return h.get(key.hashCode());
//	}
//
//	public boolean isEmpty() {
//		return h.getSize() == 0;
//	}
//
//	@Deprecated
//	public Set<K> keySet() {
//		throw new RuntimeException("rrr");
//	}
//
//	public V put(final K key, final V value) {
//		h.put(key.hashCode(), value);
//		return value;
//	}
//
//	@Deprecated
//	public void putAll(final Map<? extends K, ? extends V> m) {
//		throw new RuntimeException("rrr");
//	}
//
//	@Deprecated
//	public V remove(final Object key) {
//		throw new RuntimeException("rrr");
//	}
//
//	public int size() {
//		return h.getSize();
//	}
//
//	@Deprecated
//	public Collection<V> values() {
//		List<V> r = new ArrayList<V>();
//		for (int i = 0; i < h.size; i++) {
//			r.add(h.values[i]);
//		}
//		return r;
//	}
//
//	public Iterator<V> iterator() {
//		return h.iterator();
//	}
//
//}
