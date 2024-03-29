package org.mb.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mb.exception.OrmException;
import org.mb.query.IQueryObject;
import org.mb.query.QueryObject;
import org.mb.record.Entity;
import org.mb.record.field.Field;
import org.mb.record.field.IdentifiableField;
import org.mb.session.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataMapper {

	protected static final Logger LOG = Logger.getLogger(DataMapper.class.getName());

	public static final boolean SHOW_SQL = false;

	private DataMapper() {
	}

	public static <T extends Entity> T queryUnique(final IRowMapper<T> rowMapper, final IQueryObject query) {
		return innerQueryFirst(rowMapper, query, true);
	}

	public static <T extends Entity> T queryFirst(final IRowMapper<T> rowMapper, final IQueryObject query) {
		return innerQueryFirst(rowMapper, query, false);
	}

	protected static <T extends Entity> T innerQueryFirst(final IRowMapper<T> rowMapper, final IQueryObject query,
			final boolean unique) {
		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine(query.toString());
		}
		if (SHOW_SQL) {
			System.out.println(query.toString());
		}

		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {

			final Connection c = SessionFactory.getSession().getConnection();
			pstm = c.prepareStatement(query.getQuery());

			int columnIndex = 1;
			for (final Object p : query.getParamsList()) {
				pstm.setObject(columnIndex++, p);
			}

			rs = pstm.executeQuery();
			if (!rs.next()) {
				throw new OrmException("no row produced");
			}
			final T r = rowMapper.mapRow(rs);

			if (unique && rs.next()) {
				throw new OrmException("more than 1 row produced");
			}

			return r;
		} catch (final Exception e) {
			throw new OrmException("error in query: " + query.toString(), e);
		} finally {
			close(pstm, rs);
		}
	}

	public static <T extends Entity> List<T> query(final IRowMapper<T> rowMapper, final IQueryObject query) {
		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine(query.toString());
		}
		if (SHOW_SQL) {
			System.out.println(query.toString());
		}

		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			final Connection c = SessionFactory.getSession().getConnection();
			pstm = c.prepareStatement(query.getQuery());

			int columnIndex = 1;
			for (final Object p : query.getParamsList()) {
				pstm.setObject(columnIndex++, p);
			}

			rs = pstm.executeQuery();
			final List<T> r = new ArrayList<T>();
			while (rs.next()) {
				r.add(rowMapper.mapRow(rs));
			}

			return r;
		} catch (final Exception e) {
			throw new OrmException("error in query: " + query.toString(), e);
		} finally {
			close(pstm, rs);
		}
	}

	public static void aggregateIdentityField(final IdentifiableField<?> field, final IQueryObject query) {
		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine(query.toString());
		}
		if (SHOW_SQL) {
			System.out.println(query.toString());
		}

		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {

			final Connection c = SessionFactory.getSession().getConnection();
			pstm = c.prepareStatement(query.getQuery());

			int columnIndex = 1;
			for (final Object p : query.getParamsList()) {
				pstm.setObject(columnIndex++, p);
			}

			rs = pstm.executeQuery();
			if (!rs.next()) {
				throw new OrmException("no row produced");
			}
			field.loadIdentity(rs);

			if (rs.next()) {
				throw new OrmException("more than 1 row produced");
			}

		} catch (final Exception e) {
			throw new OrmException("error in query: " + query, e);
		} finally {
			close(pstm, rs);
		}
	}

	public static <T> T aggregate(final Field<T> field, final IQueryObject query) {
		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine(query.toString());
		}
		if (SHOW_SQL) {
			System.out.println(query.toString());
		}

		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {

			final Connection c = SessionFactory.getSession().getConnection();
			pstm = c.prepareStatement(query.getQuery());

			int columnIndex = 1;
			for (final Object p : query.getParamsList()) {
				pstm.setObject(columnIndex++, p);
			}

			rs = pstm.executeQuery();
			if (!rs.next()) {
				throw new OrmException("no row produced");
			}
			field.load(rs);

			if (rs.next()) {
				throw new OrmException("more than 1 row produced");
			}

			return field.getValue();

		} catch (final Exception e) {
			throw new OrmException("error in query: " + query, e);
		} finally {
			close(pstm, rs);
		}
	}

	/**
	 * @return number of affected rows
	 */
	public static int sqlStatement(final String query, final Object... params) {
		return update(new QueryObject(query, params));
	}

	public static int update(final IQueryObject query) {
		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine(query.toString());
		}
		if (SHOW_SQL) {
			System.out.println(query.toString());
		}

		PreparedStatement pstm = null;
		try {
			final Connection c = SessionFactory.getSession().getConnection();
			pstm = c.prepareStatement(query.getQuery());

			int columnIndex = 1;
			for (final Object p : query.getParamsList()) {
				pstm.setObject(columnIndex++, p);
			}

			final int r = pstm.executeUpdate();
			if (r == 0) {
				throw new OrmException("no affected rows");
			}
			return r;
		} catch (final Exception e) {
			throw new OrmException("error in query: " + query, e);
		} finally {
			close(pstm, null);
		}
	}

	public static int executeDDLIgnoringErrors(final String sqlQuery) {

		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine("DDL: " + sqlQuery);
		}
		if (SHOW_SQL) {
			System.out.println(sqlQuery);
		}

		PreparedStatement pstm = null;
		try {
			final Connection c = SessionFactory.getSession().getConnection();
			pstm = c.prepareStatement(sqlQuery);
			return pstm.executeUpdate();
		} catch (final Exception e) {
			return 0;
		} finally {
			close(pstm, null);
		}
	}

	public static int executeDDL(final String sqlQuery) {

		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine("DDL: " + sqlQuery);
		}
		if (SHOW_SQL) {
			System.out.println(sqlQuery);
		}

		PreparedStatement pstm = null;
		try {
			final Connection c = SessionFactory.getSession().getConnection();
			pstm = c.prepareStatement(sqlQuery);
			return pstm.executeUpdate();
		} catch (final Exception e) {
			throw new OrmException("error in DDL: " + sqlQuery, e);
		} finally {
			close(pstm, null);
		}
	}

	protected static void close(final PreparedStatement pstm, final ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstm != null) {
				pstm.close();
			}
		} catch (final Exception e) {
			// XXX ?
			throw new OrmException("error closing connection resources", e);
		}
	}

}
