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

import org.morm.exception.SormException;
import org.morm.mapper.DataMapper;
import org.morm.record.QueryObject;
import org.morm.record.field.Field;
import org.morm.record.identity.IdentityGenerator;

public class GenericMaxIdentityKey<T> extends IdentityGenerator<T> {

	protected final String tableName;

	public GenericMaxIdentityKey(final String tableName, final Field<T> field) {
		super(field);
		this.tableName = tableName;
	}

	@Override
	public QueryObject getQuery() {

		return new QueryObject()
		/**/.append("SELECT MAX(")
		/**/.append(field.getColumnName())
		/**/.append(")+1 AS ")
		/**/.append(field.getColumnName())
		/**/.append(" FROM ")
		/**/.append(tableName)
		/**/;
	}

	@Override
	public void setGeneratedValue() {

		try {

			field.setUncheckedValue(DataMapper.aggregate(super.query));

		} catch (final Exception e) {
			throw new SormException(ERROR_OBTAINING_IDENTITY_KEY + query, e);
		}
	}

	@Override
	public boolean generateBefore() {
		return true;
	}

	@Override
	public IdentityGenerator<T> doCloneId() {
		return new GenericMaxIdentityKey<T>(tableName, field.doClone());
	}

}
