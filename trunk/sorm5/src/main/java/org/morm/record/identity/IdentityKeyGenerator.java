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
package org.morm.record.identity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.QueryObject;
import org.morm.record.field.Field;

public abstract class IdentityKeyGenerator<T> extends Field<T> {

	protected static final String PRODUCED_MORE_THAN_1_IDENTITY_KEY = "produced more than 1 identity key: ";
	protected static final String NO_IDENTITY_KEY_PRODUCED = "no identity key produced?: ";
	protected static final String ERROR_OBTAINING_IDENTITY_KEY = "error obtaining identity key: ";

	protected final Field<T> fieldMeta;
	protected QueryObject query;

	public IdentityKeyGenerator(final Field<T> fieldMeta) {
		super(fieldMeta.getColumnName());
		this.fieldMeta = fieldMeta;
		this.query = getQuery();
	}

	protected abstract QueryObject getQuery();

	public abstract void setGeneratedValue();

	public abstract boolean generateBefore();

	@Override
	public T getValue() {
		return fieldMeta.getValue();
	}

	@Override
	public void setValue(final T value) {
		fieldMeta.setValue(value);
	}

	@Override
	public Field<T> doClone() {
		throw new RuntimeException();
	}

	public abstract IdentityKeyGenerator<T> doCloneId();

	@Override
	public void load(final ResultSet rs) throws SQLException {
		fieldMeta.load(rs);
	}

	@Override
	public void loadAggregate(final ResultSet rs) throws SQLException {
		fieldMeta.loadAggregate(rs);
	}

	@Override
	public void store(final PreparedStatement pstm, final int index) throws SQLException {
		fieldMeta.store(pstm, index);
	}

}