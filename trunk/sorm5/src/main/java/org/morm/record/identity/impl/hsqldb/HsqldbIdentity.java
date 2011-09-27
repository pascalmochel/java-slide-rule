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

import org.morm.exception.SormException;
import org.morm.mapper.DataMapper;
import org.morm.query.IQueryObject;
import org.morm.query.QueryObject;
import org.morm.record.field.IdentifiableField;
import org.morm.record.identity.IdentityGenerator;

public class HsqldbIdentity<T> extends IdentityGenerator<T> {

	public HsqldbIdentity(final IdentifiableField<T> field) {
		super(field);
	}

	@Override
	public IQueryObject getQuery() {
		return new QueryObject("CALL IDENTITY()");
	}

	@Override
	public void setGeneratedValue() {
		try {
			DataMapper.aggregateIdentityField(field, super.query);
		} catch (final Exception e) {
			throw new SormException(ERROR_OBTAINING_IDENTITY_KEY + query, e);
		}
	}

	@Override
	public boolean generateBefore() {
		return false;
	}

	@Override
	public IdentityGenerator<T> doCloneId() {
		return new HsqldbIdentity<T>((IdentifiableField<T>) field.doClone());
	}

}
