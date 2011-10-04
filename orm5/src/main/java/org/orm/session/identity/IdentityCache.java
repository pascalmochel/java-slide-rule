package org.orm.session.identity;

import org.orm.record.Entity;

import java.util.HashMap;
import java.util.Map;

public class IdentityCache<T extends Entity> {

	protected final Map<Class<T>, HashMap<Object, T>> cache;

	public IdentityCache() {
		this.cache = new HashMap<Class<T>, HashMap<Object, T>>();
	}

	public T attach(final Class<T> entClass, final Object idValue, final T entity) {
		if (idValue == null) {
			return entity;
		}
		HashMap<Object, T> classMap = cache.get(entClass);
		if (classMap == null) {
			classMap = new HashMap<Object, T>();
			cache.put(entClass, classMap);
		}
		if (!classMap.containsKey(idValue)) {
			classMap.put(idValue, entity);
			return entity;
		}
		return classMap.get(idValue);
	}

	public boolean isAttached(final Class<T> entClass, final Object idValue) {
		if (idValue == null) {
			return false;
		}
		final HashMap<Object, T> classMap = cache.get(entClass);
		if (classMap == null) {
			return false;
		}
		return classMap.containsKey(idValue);
	}

	public void clear() {
		for (final HashMap<Object, T> classMap : cache.values()) {
			classMap.clear();
		}
		cache.clear();
	}

}
