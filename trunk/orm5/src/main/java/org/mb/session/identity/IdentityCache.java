package org.mb.session.identity;

import org.mb.record.Entity;

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

	public T attachForce(final Class<T> entClass, final Object idValue, final T entity) {
		if (idValue == null) {
			return entity;
		}
		HashMap<Object, T> classMap = cache.get(entClass);
		if (classMap == null) {
			classMap = new HashMap<Object, T>();
			cache.put(entClass, classMap);
		}
		classMap.put(idValue, entity);
		return entity;
	}

	public T isAttached(final Class<T> entClass, final Object idValue) {
		if (idValue == null) {
			return null;
		}
		final HashMap<Object, T> classMap = cache.get(entClass);
		if (classMap == null) {
			return null;
		}
		return classMap.get(idValue);
	}

	public void clear() {
		for (final HashMap<Object, T> classMap : cache.values()) {
			classMap.clear();
		}
		cache.clear();
	}

}
