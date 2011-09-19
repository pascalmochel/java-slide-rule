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

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.exception.SormException;
import org.morm.query.IQueryObject;
import org.morm.record.field.Field;

public abstract class IdentityGenerator<T> extends Field<T> {

	protected static final String PRODUCED_MORE_THAN_1_IDENTITY_KEY = "produced more than 1 identity key: ";
	protected static final String NO_IDENTITY_KEY_PRODUCED = "no identity key produced?: ";
	protected static final String ERROR_OBTAINING_IDENTITY_KEY = "error obtaining identity key: ";

	protected final Field<T> field;
	protected IQueryObject query;

	public IdentityGenerator(final Field<T> field) {
		super(field.getColumnName());
		this.field = field;
		this.query = null;
	}

	public void assignGeneratedValue() {
		if (this.query == null) {
			this.query = getQuery();
		}
		setGeneratedValue();
	}

	protected abstract void setGeneratedValue();

	protected abstract IQueryObject getQuery();

	public abstract boolean generateBefore();

	@Override
	public T getValue() {
		return field.getValue();
	}

	@Override
	public void setValue(final T value) {
		field.setValue(value);
	}

	@Override
	public Field<T> doClone() {
		throw new SormException("internal error, invoking " + getClass() + "#doClone()");
	}

	public abstract IdentityGenerator<T> doCloneId();

	@Override
	public void load(final ResultSet rs) throws SQLException {
		field.load(rs);
	}

}
