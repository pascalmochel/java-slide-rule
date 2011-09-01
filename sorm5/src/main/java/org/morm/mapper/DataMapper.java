package org.morm.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.morm.record.QueryObject;
import org.morm.session.SessionFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataMapper {

	public static <T> T queryUnique(final IRowMapper<T> rowMapper, QueryObject query) {
		return queryUnique(rowMapper, query.getQuery(), query.getParams());
	}

	public static <T> List<T> query(final IRowMapper<T> rowMapper, QueryObject query) {
		return query(rowMapper, query.getQuery(), query.getParams());
	}

	public static Number aggregate(QueryObject query) {
		return aggregate(query.getQuery(), query.getParams());
	}

	public static int update(QueryObject query) {
		return update(query.getQuery(), query.getParams());
	}

	public static <T> T queryUnique(final IRowMapper<T> rowMapper, final String sqlQuery,
			final Object[] params) {

		System.out.println("executing: " + sqlQuery + " -- " + Arrays.toString(params));

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
				throw new RuntimeException("no row produced");
			}
			final T r = rowMapper.mapRow(rs);

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

	public static <T> List<T> query(final IRowMapper<T> rowMapper, final String sqlQuery,
			final Object[] params) {

		System.out.println("executing: " + sqlQuery + " -- " + Arrays.toString(params));

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
			throw new RuntimeException(e);
		} finally {
			close(pstm, rs);
		}
	}

	public static Number aggregate(final String sqlQuery, final Object[] params) {

		System.out.println("executing: " + sqlQuery + " -- " + Arrays.toString(params));

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
				throw new RuntimeException("no row produced");
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

	public static int update(final String sqlQuery, final Object[] params) {

		System.out.println("executing: " + sqlQuery + " -- " + Arrays.toString(params));

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
