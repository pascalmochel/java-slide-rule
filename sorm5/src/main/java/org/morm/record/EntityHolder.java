package org.morm.record;

import java.util.HashMap;
import java.util.Map;

public class EntityHolder {

	private EntityHolder() {
	}

	protected static ThreadLocal<Map<Class<Entity>, Entity>> instancesLookup = new ThreadLocal<Map<Class<Entity>, Entity>>() {
		@Override
		protected Map<Class<Entity>, Entity> initialValue() {
			return new HashMap<Class<Entity>, Entity>();
		};
	};

	@SuppressWarnings("unchecked")
	public static <T extends Entity> T getInstance(final Class<T> entityClass) {
		try {
			if (instancesLookup.get().get(entityClass) == null) {
				instancesLookup.get().put((Class<Entity>) entityClass, entityClass.newInstance());
			}
			return (T) instancesLookup.get().get(entityClass);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
