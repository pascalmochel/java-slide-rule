package org.mb.collection;
//package org.mb.collection;
//
//import java.lang.reflect.Array;
//
//import java.util.Arrays;
//import java.util.Iterator;
//
//public class H<T> {
//
//	protected Class<T> elementClass;
//	protected int capacity;
//	protected int extendsBy;
//	protected int size;
//
//	protected T[] values;
//	protected Slot[] hashTable;
//
//	@SuppressWarnings("unchecked")
//	public H(final Class<T> elementClass, final int capacity, final int extendsBy) {
//		this.elementClass = elementClass;
//		this.capacity = capacity;
//		this.extendsBy = extendsBy;
//		this.size = 0;
//
//		this.values = (T[]) Array.newInstance(elementClass, capacity);
//
//		this.hashTable = (Slot[]) Array.newInstance(Slot.class, capacity);
//		for (int i = 0; i < capacity; i++) {
//			this.hashTable[i] = new Slot(elementClass, 0, 1);
//		}
//	}
//
//	protected int hash(final int hashCode) {
//		return (hashCode * 97) % capacity;
//	}
//
//	/**
//	 * més d'un put() del mateix objecte fa que es dupliqui en struct de hash
//	 */
//	public void put(final int keyCode, final T e) {
//		if (size == values.length) {
//			this.values = Arrays.copyOf(this.values, size + extendsBy);
//		}
//		this.values[size++] = e;
//		this.hashTable[hash(keyCode)].add(keyCode, e);
//	}
//
//	public T get(final int keyCode) {
//		return this.hashTable[hash(keyCode)].get(keyCode);
//	}
//
//	protected class Slot {
//
//		protected Class<T> elementClass;
//		protected int initialCapacity;
//		protected int extendsBy;
//
//		protected int[] hashCodes;
//		protected T[] values;
//		protected int size;
//
//		@SuppressWarnings("unchecked")
//		public Slot(final Class<T> elementClass, final int initialCapacity, final int extendsBy) {
//			super();
//			this.elementClass = elementClass;
//			this.initialCapacity = initialCapacity;
//			this.extendsBy = extendsBy;
//
//			this.hashCodes = new int[initialCapacity];
//			this.values = (T[]) Array.newInstance(elementClass, initialCapacity);
//			this.size = 0;
//		}
//
//		public void add(final int hashCode, final T value) {
//			if (size == values.length) {
//				this.hashCodes = Arrays.copyOf(this.hashCodes, size + extendsBy);
//				this.values = Arrays.copyOf(this.values, size + extendsBy);
//			}
//			this.hashCodes[size] = hashCode;
//			this.values[size] = value;
//			size++;
//		}
//
//		public T get(final int hashCode) {
//			for (int i = 0; i < size; i++) {
//				if (hashCode == hashCodes[i]) {
//					return values[i];
//				}
//			}
//			return null;
//		}
//
//		@Override
//		public String toString() {
//			return Arrays.toString(this.values);
//		}
//	}
//
//	@Override
//	public String toString() {
//		final StringBuilder r = new StringBuilder();
//		r.append('[');
//		for (int i = 0; i < capacity; i++) {
//			r.append(i);
//			r.append(':');
//			r.append(hashTable[i]);
//			r.append(", ");
//		}
//		r.append(')');
//		return r.toString();
//	}
//
//	public Iterator<T> iterator() {
//		return new Iterator<T>() {
//			protected int i = 0;
//
//			public boolean hasNext() {
//				return i < size;
//			}
//
//			public T next() {
//				return values[i++];
//			}
//
//			public void remove() {
//				throw new RuntimeException("rrr");
//			}
//		};
//	}
//
//	public int getSize() {
//		return size;
//	}
//
//}
