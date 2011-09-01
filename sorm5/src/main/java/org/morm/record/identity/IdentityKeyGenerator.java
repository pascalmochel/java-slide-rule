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

import org.morm.record.field.Field;

public abstract class IdentityKeyGenerator {

	protected static final String PRODUCED_MORE_THAN_1_IDENTITY_KEY = "produced more than 1 identity key: ";
	protected static final String NO_IDENTITY_KEY_PRODUCED = "no identity key produced?: ";
	protected static final String ERROR_OBTAINING_IDENTITY_KEY = "error obtaining identity key: ";

	protected String query;
	protected Field<?> fieldMeta;

	public void registerField(final Field<?> fieldMeta) {

		this.fieldMeta = fieldMeta;
		this.query = getQuery();
	}

	public abstract String getQuery();

	public abstract void getGeneratedValue();

	public abstract boolean generateBefore();

}