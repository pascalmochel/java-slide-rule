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
package org.morm.record.identity.impl.hsqldb;

import org.morm.exception.FException;
import org.morm.mapper.DataMapper;
import org.morm.record.QueryObject;
import org.morm.record.field.Field;
import org.morm.record.identity.IdentityKeyGenerator;

import java.util.logging.Logger;

public class HsqldbSequence<T> extends IdentityKeyGenerator<T> {

	private final static Logger LOG = Logger.getLogger(HsqldbSequence.class.getName());

	protected final String sequenceName;

	public HsqldbSequence(final Field<T> fieldMeta, final String sequenceName) {
		super(fieldMeta);
		this.sequenceName = sequenceName;
	}

	@Override
	public QueryObject getQuery() {

		return new QueryObject()
		/**/.append("CALL NEXT VALUE FOR ")
		/**/.append(sequenceName)
		/**/;
	}

	@Override
	public void setGeneratedValue() {

		LOG.finer("[S] " + query);

		try {

			fieldMeta.setUncheckedValue(DataMapper.aggregate(query));

		} catch (final Exception e) {
			throw new FException(ERROR_OBTAINING_IDENTITY_KEY + query, e);
		}
	}

	public String getSequenceName() {
		return sequenceName;
	}

	@Override
	public boolean generateBefore() {
		return true;
	}

	@Override
	public IdentityKeyGenerator<T> doCloneId() {
		return new HsqldbSequence<T>(fieldMeta.doClone(), sequenceName);
	}

}
