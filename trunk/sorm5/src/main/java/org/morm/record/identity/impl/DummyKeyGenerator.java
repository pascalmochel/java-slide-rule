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

import org.morm.exception.FException;
import org.morm.record.QueryObject;
import org.morm.record.field.Field;
import org.morm.record.identity.IdentityKeyGenerator;

public class DummyKeyGenerator<T> extends IdentityKeyGenerator<T> {

	public DummyKeyGenerator(final Field<T> fieldMeta) {
		super(fieldMeta);
	}

	@Override
	public boolean generateBefore() {
		throw new FException(this.getClass().getSimpleName()
				+ " is a dummy object that dont supports this feature");
	}

	@Override
	public void setGeneratedValue() {
		throw new FException(this.getClass().getSimpleName()
				+ " is a dummy object that dont supports this feature");
	}

	@Override
	protected QueryObject getQuery() {
		return null;
	}

	@Override
	public IdentityKeyGenerator<T> doCloneId() {
		return new DummyKeyGenerator<T>(fieldMeta.doClone());
	}

}
