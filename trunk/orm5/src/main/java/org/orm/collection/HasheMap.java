package org.orm.collection;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HasheMap<T> implements Iterable<T> {

	protected int capacity;
	protected LinkeRow<T>[] h;

	@SuppressWarnings("unchecked")
	public HasheMap(final Class<T> elementClass, final int capacity) {
		this.capacity = capacity;
		this.h = (LinkeRow<T>[]) Array.newInstance(LinkeRow.class, capacity);
		for (int i = 0; i < capacity; i++) {
			this.h[i] = new LinkeRow<T>(elementClass, 0, 1);
		}
	}

	protected int hash(final int hashCode) {
		return (hashCode * 97) % capacity;
	}

	public void put(final int keyCode, final T e) {
		this.h[hash(keyCode)].add(keyCode, e);
	}

	public T get(final int keyCode) {
		final LinkeRow<T> l = this.h[hash(keyCode)];
		return l.get(keyCode);
	}

	@Override
	public String toString() {
		final StringBuilder r = new StringBuilder();
		r.append('[');
		for (int i = 0; i < capacity; i++) {
			r.append(i);
			r.append(':');
			r.append(h[i]);
			r.append(", ");
		}
		r.append(')');
		return r.toString();
	}

	class I implements Iterator<T> {

		protected List<T> ts;
		protected int index;

		public I(final LinkeRow<T>[] ls) {
			index = 0;
			ts = new ArrayList<T>();
			for (final LinkeRow<T> l : ls) {
				for (final T v : l) {
					ts.add(v);
				}
			}
		}

		@Override
		public boolean hasNext() {
			return index < ts.size();
		}

		@Override
		public T next() {
			return ts.get(index++);
		}

		@Override
		public void remove() {
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new I(h);
	}

}
