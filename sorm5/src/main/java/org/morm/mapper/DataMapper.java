package org.morm.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.morm.exception.SormException;
import org.morm.record.Entity;
import org.morm.record.QueryObject;
import org.morm.session.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DataMapper {

	protected static final Logger LOG = Logger.getLogger(DataMapper.class.getName());

	public static <T extends Entity> T queryUnique(final IRowMapper<T> rowMapper, final QueryObject query) {
		LOG.fine(query.toString());
		// return queryUnique(rowMapper, query.getQuery(), query.getParams());

		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {

			final Connection c = SessionFactory.getCurrentSession().getConnection();
			pstm = c.prepareStatement(query.getQuery());

			int columnIndex = 1;
			for (final Object p : query.getParams()) {
				pstm.setObject(columnIndex++, p);
			}

			rs = pstm.executeQuery();
			if (!rs.next()) {
				throw new SormException("no row produced");
			}
			final T r = rowMapper.mapRow(rs);

			if (rs.next()) {
				throw new SormException("more than 1 row produced");
			}

			return r;
		} catch (final Exception e) {
			throw new SormException("error in query: " + query.toString(), e);
		} finally {
			close(pstm, rs);
		}
	}

	public static <T extends Entity> List<T> query(final IRowMapper<T> rowMapper, final QueryObject query) {
		LOG.fine(query.toString());
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			final Connection c = SessionFactory.getCurrentSession().getConnection();
			pstm = c.prepareStatement(query.getQuery());

			int columnIndex = 1;
			for (final Object p : query.getParams()) {
				pstm.setObject(columnIndex++, p);
			}

			rs = pstm.executeQuery();
			final List<T> r = new ArrayList<T>();
			while (rs.next()) {
				r.add(rowMapper.mapRow(rs));
			}

			return r;
		} catch (final Exception e) {
			throw new SormException("error in query: " + query.toString(), e);
		} finally {
			close(pstm, rs);
		}
	}

	public static Number aggregate(final QueryObject query) {
		LOG.fine(query.toString());
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {

			final Connection c = SessionFactory.getCurrentSession().getConnection();
			pstm = c.prepareStatement(query.getQuery());

			int columnIndex = 1;
			for (final Object p : query.getParams()) {
				pstm.setObject(columnIndex++, p);
			}

			rs = pstm.executeQuery();
			if (!rs.next()) {
				throw new SormException("no row produced");
			}
			final Number r = (Number) rs.getObject(1);

			if (rs.next()) {
				throw new SormException("more than 1 row produced");
			}

			return r;

		} catch (final Exception e) {
			throw new SormException("error in query: " + query, e);
		} finally {
			close(pstm, rs);
		}
	}

	public static int update(final QueryObject query) {
		LOG.fine(query.toString());
		PreparedStatement pstm = null;
		try {
			final Connection c = SessionFactory.getCurrentSession().getConnection();
			pstm = c.prepareStatement(query.getQuery());

			int columnIndex = 1;
			for (final Object p : query.getParams()) {
				pstm.setObject(columnIndex++, p);
			}

			final int r = pstm.executeUpdate();
			if (r == 0) {
				throw new SormException("no affected rows");
			}
			return r;
		} catch (final Exception e) {
			throw new SormException("error in query: " + query, e);
		} finally {
			close(pstm, null);
		}
	}

	public static int executeDDLIgnoringErrors(final String sqlQuery) {

		LOG.fine("DDL: " + sqlQuery);

		PreparedStatement pstm = null;
		try {
			final Connection c = SessionFactory.getCurrentSession().getConnection();
			pstm = c.prepareStatement(sqlQuery);
			return pstm.executeUpdate();
		} catch (final Exception e) {
			return 0;
		} finally {
			close(pstm, null);
		}
	}

	public static int executeDDL(final String sqlQuery) {

		LOG.fine("DDL: " + sqlQuery);

		PreparedStatement pstm = null;
		try {
			final Connection c = SessionFactory.getCurrentSession().getConnection();
			pstm = c.prepareStatement(sqlQuery);
			return pstm.executeUpdate();
		} catch (final Exception e) {
			throw new SormException("error in DDL: " + sqlQuery, e);
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
		} catch (final Exception e2) {
			// TODO ?
		}
	}

}
