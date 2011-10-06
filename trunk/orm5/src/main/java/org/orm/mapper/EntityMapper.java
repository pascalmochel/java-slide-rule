package org.orm.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.orm.exception.OrmException;
import org.orm.record.Entity;
import org.orm.record.field.Field;
import org.orm.session.SessionFactory;

public class EntityMapper implements IRowMapper<Entity> {

	protected Class<? extends Entity> tableClass;

	public EntityMapper(final Class<? extends Entity> tableClass) {
		this.tableClass = tableClass;
	}

	@SuppressWarnings("unchecked")
	public Entity mapRow(final ResultSet rs) throws SQLException {
		try {
			final Entity r = tableClass.newInstance();

			/**
			 * carrega la columna d'id, i mira si la té cached
			 */
			r.getIdField().load(rs);
			final Object idValue = r.getIdField().getValue();
			final Entity attached = SessionFactory.getSession().getIdCache().isAttached(
					(Class<Entity>) tableClass, idValue);
			if (attached != null) {
				return attached;
			}

			for (final Field<?> f : r.getFields()) {
				try {
					f.load(rs);
				} catch (final Exception e) {
					throw new OrmException("error mapping field: " + f.toString(), e);
				}
			}
			final Class<Entity> entityClass = (Class<Entity>) r.getClass();
			return SessionFactory.getSession().getIdCache().attach(entityClass, r.getIdField().getValue(), r);

		} catch (final Exception e) {
			throw new OrmException("error mapping " + getClass().getSimpleName(), e);
		}
	}

}