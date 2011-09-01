/*
 * Copyright (C) 2009, 2010 M. Lechouga
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.morm.record.identity.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.morm.mapper.DataMapper;
import org.morm.record.identity.IdentityKeyGenerator;
import org.morm.session.SessionFactory;

import java.util.logging.Logger;

public class HsqldbIdentity extends IdentityKeyGenerator {

	private final static Logger LOG = Logger.getLogger(HsqldbIdentity.class.getName());

	@Override
	public String getQuery() {
		return "CALL IDENTITY()";
	}

	@Override
	public void getGeneratedValue() {

		LOG.finer("[S] " + query);
		try {

			final Connection con = SessionFactory.getCurrentSession().getConnection();
			final PreparedStatement ps = con.prepareStatement(query);
			final ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				throw new RuntimeException(NO_IDENTITY_KEY_PRODUCED + query);
			}

			// fieldMeta.getColumnFromScalarQuery(rs);
			fieldMeta.loadAggregate(rs);

			if (rs.next()) {
				throw new RuntimeException(PRODUCED_MORE_THAN_1_IDENTITY_KEY + query);
			}

			// RecordOps.closeStuff(ps, rs);
			DataMapper.close(ps, rs);

		} catch (final Exception e) {
			throw new RuntimeException(ERROR_OBTAINING_IDENTITY_KEY + query, e);
		}

	}

	@Override
	public boolean generateBefore() {
		return false;
	}

}