package org.morm.record;

import org.morm.exception.SormException;

import java.util.HashMap;
import java.util.Map;

public class SingletonFactory {

	protected static final ThreadLocal<Map<Class<? extends Entity>, Entity>> entities = new ThreadLocal<Map<Class<? extends Entity>, Entity>>() {
		@Override
		protected Map<java.lang.Class<? extends Entity>, Entity> initialValue() {
			return new HashMap<Class<? extends Entity>, Entity>();
		};
	};

	@SuppressWarnings("unchecked")
	public static <T extends Entity> T get(final Class<T> c) {
		Map<Class<? extends Entity>, Entity> map = entities.get();
		if (!map.containsKey(c)) {
			try {
				final Entity r = c.newInstance();
				map.put(c, r);
			} catch (final Exception e) {
				throw new SormException("error instancing: " + c.getName(), e);
			}
		}
		return (T) map.get(c);
	}

}
