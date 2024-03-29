package org.mb.record;

import org.mb.record.field.Field;
import org.mb.record.field.FieldDef;

import java.util.Collection;

public class QueryGenUtils {

	private QueryGenUtils() {
	}

	public static String columnNamesJoin(final Collection<Field<?>> fields) {

		final StringBuilder r = new StringBuilder();
		for (final FieldDef<?> f : fields) {
			r.append(f.getColumnName()).append(',');
		}
		return r.deleteCharAt(r.length() - 1).toString();
	}

	// public static String columnNamesJoin(final FieldDef<?>[] fields) {
	//
	// final StringBuilder r = new StringBuilder();
	// for (final FieldDef<?> f : fields) {
	// r.append(f.getColumnName()).append(',');
	// }
	// return r.deleteCharAt(r.length() - 1).toString();
	// }

	public static String parametersJoin(final Collection<Field<?>> fields) {

		final StringBuilder r = new StringBuilder();
		for (@SuppressWarnings("unused")
		final FieldDef<?> f : fields) {
			r.append("?,");
		}
		return r.deleteCharAt(r.length() - 1).toString();
	}

	public static String setColumnNamesExceptId(final FieldDef<?> idField, final Collection<Field<?>> fields) {

		final StringBuilder r = new StringBuilder();
		for (final FieldDef<?> f : fields) {
			if (!f.equals(idField)) {
				r.append(f.getColumnName()).append("=?,");
			}
		}
		return r.deleteCharAt(r.length() - 1).toString();
	}

	public static Object[] fieldValues(final Collection<Field<?>> fields) {
		final Object[] r = new Object[fields.size()];
		int i = 0;
		for (final Field<?> f : fields) {
			r[i] = f.getValue();
			i++;
		}
		return r;
	}

	public static Object[] fieldValuesIdLast(final Field<?> idField, final Collection<Field<?>> fields) {
		final Object[] r = new Object[fields.size()];
		int i = 0;
		for (final Field<?> f : fields) {
			if (!f.equals(idField)) {
				r[i] = f.getValue();
				i++;
			}
		}
		r[i] = idField.getValue();
		return r;
	}

	public static String join(final String s, final String sep, final int times) {

		final StringBuilder r = new StringBuilder();
		for (int i = 0; i < times; i++) {
			r.append(s).append(sep);
		}
		return r.deleteCharAt(r.length() - sep.length()).toString();
	}

}
