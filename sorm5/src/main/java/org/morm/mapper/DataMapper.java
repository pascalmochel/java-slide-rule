package org.morm.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.morm.exception.FrijolesException;
import org.morm.record.QueryObject;
import org.morm.session.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class DataMapper {

	public static <T> T queryUnique(final IRowMapper<T> rowMapper, final QueryObject query) {
		System.out.println(query.toString());
		return queryUnique(rowMapper, query.getQuery(), query.getParams());
	}

	public static <T> List<T> query(final IRowMapper<T> rowMapper, final QueryObject query) {
		System.out.println(query.toString());
		return query(rowMapper, query.getQuery(), query.getParams());
	}

	public static Number aggregate(final QueryObject query) {
		System.out.println(query.toString());
		return aggregate(query.getQuery(), query.getParams());
	}

	public static int update(final QueryObject query) {
		System.out.println(query.toString());
		return update(query.getQuery(), query.getParams());
	}

	protected static <T> T queryUnique(final IRowMapper<T> rowMapper, final String sqlQuery,
			final Object[] params) {

		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {

			final Connection c = SessionFactory.getCurrentSession().getConnection();
			pstm = c.prepareStatement(sqlQuery);

			int columnIndex = 1;
			for (final Object p : params) {
				pstm.setObject(columnIndex++, p);
			}

			rs = pstm.executeQuery();
			if (!rs.next()) {
				throw new FrijolesException("no row produced");
			}
			final T r = rowMapper.mapRow(rs);

			if (rs.next()) {
				throw new FrijolesException("more than 1 row produced");
			}

			return r;
		} catch (final Exception e) {
			throw new FrijolesException(e);
		} finally {
			close(pstm, rs);
		}
	}

	protected static <T> List<T> query(final IRowMapper<T> rowMapper, final String sqlQuery,
			final Object[] params) {

		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			final Connection c = SessionFactory.getCurrentSession().getConnection();
			pstm = c.prepareStatement(sqlQuery);

			int columnIndex = 1;
			for (final Object p : params) {
				pstm.setObject(columnIndex++, p);
			}

			rs = pstm.executeQuery();
			final List<T> r = new ArrayList<T>();
			while (rs.next()) {
				r.add(rowMapper.mapRow(rs));
			}

			return r;
		} catch (final Exception e) {
			throw new FrijolesException(e);
		} finally {
			close(pstm, rs);
		}
	}

	protected static Number aggregate(final String sqlQuery, final Object[] params) {

		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {

			final Connection c = SessionFactory.getCurrentSession().getConnection();
			pstm = c.prepareStatement(sqlQuery);

			int columnIndex = 1;
			for (final Object p : params) {
				pstm.setObject(columnIndex++, p);
			}

			rs = pstm.executeQuery();
			if (!rs.next()) {
				throw new FrijolesException("no row produced");
			}
			final Number r = (Number) rs.getObject(1);

			if (rs.next()) {
				throw new RuntimeException("more than 1 row produced");
			}

			return r;

		} catch (final Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(pstm, rs);
		}
	}

	protected static int update(final String sqlQuery, final Object[] params) {

		PreparedStatement pstm = null;
		try {
			final Connection c = SessionFactory.getCurrentSession().getConnection();
			pstm = c.prepareStatement(sqlQuery);

			int columnIndex = 1;
			for (final Object p : params) {
				pstm.setObject(columnIndex++, p);
			}

			final int r = pstm.executeUpdate();
			if (r == 0) {
				throw new RuntimeException("no affected rows");
			}
			return r;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(pstm, null);
		}
	}

	public static int executeDDL(final String sqlQuery) {

		System.out.println("executing: " + sqlQuery);

		try {
			final Connection c = SessionFactory.getCurrentSession().getConnection();
			return c.prepareStatement(sqlQuery).executeUpdate();
		} catch (final Exception e) {
			throw new RuntimeException(e);
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
