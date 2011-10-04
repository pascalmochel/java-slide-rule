package org.orm.record;

import org.orm.exception.OrmException;
import org.orm.mapper.EntityMapper;
import org.orm.mapper.IRowMapper;
import org.orm.query.MutableQueryObject;

import java.util.HashMap;
import java.util.Map;

public class SingletonFactory {

	protected static final ThreadLocal<Map<Class<? extends Entity>, Entity>> entities =
	/**/new ThreadLocal<Map<Class<? extends Entity>, Entity>>() {
		@Override
		protected Map<java.lang.Class<? extends Entity>, Entity> initialValue() {
			return new HashMap<Class<? extends Entity>, Entity>();
		}
	};
	protected static final ThreadLocal<Map<Class<? extends Entity>, IRowMapper<Entity>>> entityMappers =
	/**/new ThreadLocal<Map<Class<? extends Entity>, IRowMapper<Entity>>>() {
		@Override
		protected Map<java.lang.Class<? extends Entity>, IRowMapper<Entity>> initialValue() {
			return new HashMap<Class<? extends Entity>, IRowMapper<Entity>>();
		}
	};

	protected static final ThreadLocal<Map<Class<? extends Entity>, Map<String, MutableQueryObject>>> threadLocalQueries =
	/**/new ThreadLocal<Map<Class<? extends Entity>, Map<String, MutableQueryObject>>>() {
		@Override
		protected Map<Class<? extends Entity>, Map<String, MutableQueryObject>> initialValue() {
			return new HashMap<Class<? extends Entity>, Map<String, MutableQueryObject>>();
		}
	};

	@SuppressWarnings("unchecked")
	public static <T extends Entity> T getEntity(final Class<T> c) {
		final Map<Class<? extends Entity>, Entity> entMap = entities.get();
		if (!entMap.containsKey(c)) {
			try {
				final Entity r = c.newInstance();
				entMap.put(c, r);
				return (T) r;
			} catch (final Exception e) {
				throw new OrmException("error instancing: " + c.getName(), e);
			}
		}
		return (T) entMap.get(c);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Entity> IRowMapper<T> getEntityMapper(final Class<T> c) {
		final Map<Class<? extends Entity>, IRowMapper<Entity>> mappersMap = entityMappers.get();
		if (!mappersMap.containsKey(c)) {
			try {
				final EntityMapper r = new EntityMapper(c);
				mappersMap.put(c, r);
				return (IRowMapper<T>) r;
			} catch (final Exception e) {
				throw new OrmException("error obtaining EntityMapper for entity: " + c.getName(), e);
			}
		}
		return (IRowMapper<T>) mappersMap.get(c);
	}

	public static <T extends Entity> boolean queryIsDefined(final Class<T> c, final String name) {
		final Map<Class<? extends Entity>, Map<String, MutableQueryObject>> entMap = threadLocalQueries.get();
		if (!entMap.containsKey(c)) {
			final Map<String, MutableQueryObject> queriesMap = new HashMap<String, MutableQueryObject>();
			entMap.put(c, queriesMap);
			return false;
		}
		final Map<String, MutableQueryObject> queriesMap = entMap.get(c);
		return queriesMap.containsKey(name);
	}

	public static <T extends Entity> MutableQueryObject queryGet(final Class<T> c, final String name) {
		final Map<Class<? extends Entity>, Map<String, MutableQueryObject>> entMap = threadLocalQueries.get();
		final Map<String, MutableQueryObject> queriesMap = entMap.get(c);
		return queriesMap.get(name);
	}

	public static <T extends Entity> void querySet(final Class<T> c, final String name,
			final MutableQueryObject query) {
		final Map<Class<? extends Entity>, Map<String, MutableQueryObject>> entMap = threadLocalQueries.get();
		if (!entMap.containsKey(c)) {
			final Map<String, MutableQueryObject> queriesMap = new HashMap<String, MutableQueryObject>();
			entMap.put(c, queriesMap);
		}
		final Map<String, MutableQueryObject> queriesMap = entMap.get(c);
		queriesMap.put(name, query);
	}

}
