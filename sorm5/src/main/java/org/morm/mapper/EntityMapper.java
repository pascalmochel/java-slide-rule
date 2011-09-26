package org.morm.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.exception.SormException;
import org.morm.record.Entity;
import org.morm.record.field.Field;
import org.morm.session.SessionFactory;

public class EntityMapper implements IRowMapper<Entity> {

	protected Class<? extends Entity> tableClass;

	public EntityMapper(final Class<? extends Entity> tableClass) {
		this.tableClass = tableClass;
	}

	public Entity mapRow(final ResultSet rs) throws SQLException {
		try {
			final Entity r = tableClass.newInstance();

			for (final Field<?> f : r.getFields()) {
				try {
					f.load(rs);
				} catch (final Exception e) {
					throw new SormException("error mapping field: " + f.toString(), e);
				}
			}
			return SessionFactory.getSession().getIdentityMap().loadOrStore(r);

		} catch (final Exception e) {
			throw new SormException("error mapping " + getClass().getSimpleName(), e);
		}
	}

}