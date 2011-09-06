package org.morm.record;

import java.util.HashMap;
import java.util.Map;

public class SingletonFactory {

	protected static ThreadLocal<Map<Class<Entity>, Entity>> entities = new ThreadLocal<Map<Class<Entity>, Entity>>() {
		@Override
		protected Map<java.lang.Class<Entity>, Entity> initialValue() {
			return new HashMap<java.lang.Class<Entity>, Entity>();
		};
	};

	public static Entity get(final Class<Entity> c) {
		if (!entities.get().containsKey(c)) {
			try {
				final Entity r = c.newInstance();
				entities.get().put(c, r);
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
		}
		return entities.get().get(c);
	}

}
