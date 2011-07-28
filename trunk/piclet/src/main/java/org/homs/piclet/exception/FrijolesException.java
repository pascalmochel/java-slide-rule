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
package org.homs.piclet.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class FrijolesException extends RuntimeException {

	private static final long serialVersionUID = 1835360421671345458L;

	public FrijolesException(final String msg) {
		super(msg);
	}

	public FrijolesException(final String msg, final Throwable exc) {
		super(msg, exc);
	}

	/**
	 * shows a brief resume of nested exceptions
	 * 
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return getBriefListTrace() + super.toString();
	}

	public String stackTraceToString() {
		return stackTraceToString(this);
	}

	public String getBriefListTrace() {
		return getBriefListTrace(this);
	}

	public static String stackTraceToString(final Throwable t) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream p = new PrintStream(baos);
		t.printStackTrace(p);
		return baos.toString();
	}

	public static String getBriefListTrace(final Throwable t) {

		final StringBuilder strb = new StringBuilder(200);

		Throwable throwable = t;
		while (throwable != null) {
			if (throwable != t) {
				strb.append(";\n[cause is:] ");
			}
			strb.append(throwable.getClass().getSimpleName()) //
					.append(": ") //
					.append(throwable.getMessage());

			throwable = throwable.getCause();
		}
		strb.append('\n');
		return strb.toString();
	}
}
