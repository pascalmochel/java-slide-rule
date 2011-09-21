package org.morm.record;

import org.morm.exception.SormException;
import org.morm.mapper.EntityMapper;
import org.morm.mapper.IRowMapper;

import java.util.HashMap;
import java.util.Map;

public class SingletonFactory {

	protected static final ThreadLocal<Map<Class<? extends Entity>, Entity>> ENTITIES =
	/**/new ThreadLocal<Map<Class<? extends Entity>, Entity>>() {
		@Override
		protected Map<java.lang.Class<? extends Entity>, Entity> initialValue() {
			return new HashMap<Class<? extends Entity>, Entity>();
		};
	};
	protected static final ThreadLocal<Map<Class<? extends Entity>, IRowMapper<Entity>>> ENTITY_MAPPERS =
	/**/new ThreadLocal<Map<Class<? extends Entity>, IRowMapper<Entity>>>() {
		@Override
		protected Map<java.lang.Class<? extends Entity>, IRowMapper<Entity>> initialValue() {
			return new HashMap<Class<? extends Entity>, IRowMapper<Entity>>();
		};
	};

	@SuppressWarnings("unchecked")
	public static <T extends Entity> T getEntity(final Class<T> c) {
		final Map<Class<? extends Entity>, Entity> entMap = ENTITIES.get();
		if (!entMap.containsKey(c)) {
			try {
				final Entity r = c.newInstance();
				entMap.put(c, r);
				return (T) r;
			} catch (final Exception e) {
				throw new SormException("error instancing: " + c.getName(), e);
			}
		}
		return (T) entMap.get(c);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Entity> IRowMapper<T> getEntityMapper(final Class<T> c) {
		final Map<Class<? extends Entity>, IRowMapper<Entity>> mappersMap = ENTITY_MAPPERS.get();
		if (!mappersMap.containsKey(c)) {
			try {
				final EntityMapper r = new EntityMapper(c);
				mappersMap.put(c, r);
				return (IRowMapper<T>) r;
			} catch (final Exception e) {
				throw new SormException("error obtaining EntityMapper for entity: " + c.getName(), e);
			}
		}
		return (IRowMapper<T>) mappersMap.get(c);
	}

}
