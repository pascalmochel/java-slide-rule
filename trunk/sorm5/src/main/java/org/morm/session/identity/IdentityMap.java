package org.morm.session.identity;

import org.morm.record.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdentityMap<T extends Entity> {

	protected Map<Class<T>, HashMap<Object, T>> entityMap;

	public IdentityMap() {
		super();
		this.entityMap = new HashMap<Class<T>, HashMap<Object, T>>();
	}

	@SuppressWarnings("unchecked")
	public T loadOrStore(final T entity) {
		final Object idValue = entity.getIdField().getValue();
		if (idValue == null) {
			return entity;
		}

		final Class<T> ec = (Class<T>) entity.getClass();
		HashMap<Object, T> instancesMap = entityMap.get(ec);
		if (instancesMap == null) {
			instancesMap = new HashMap<Object, T>();
			entityMap.put(ec, instancesMap);
		}

		final T r = instancesMap.get(idValue);
		if (r == null) {
			instancesMap.put(idValue, entity);
			return entity;
		}
		return r;
	}

	public List<T> loadOrStore(final List<T> entitiesList) {
		for (int i = 0; i < entitiesList.size(); i++) {
			final T e = loadOrStore(entitiesList.get(i));
			entitiesList.set(i, e);
		}
		return entitiesList;
	}

	@SuppressWarnings("unchecked")
	public void store(final T entity) {
		final Object idValue = entity.getIdField().getValue();
		if (idValue == null) {
			return;
		}

		final Class<T> ec = (Class<T>) entity.getClass();
		HashMap<Object, T> instancesMap = entityMap.get(ec);
		if (instancesMap == null) {
			instancesMap = new HashMap<Object, T>();
			entityMap.put(ec, instancesMap);
		}

		instancesMap.put(idValue, entity);
	}

	public void clear() {
		for (final HashMap<Object, T> i : entityMap.values()) {
			i.clear();
		}
	}

}
