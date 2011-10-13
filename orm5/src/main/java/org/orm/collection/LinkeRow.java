//package org.orm.collection;
//
//import java.lang.reflect.Array;
//
//import java.util.Arrays;
//import java.util.Iterator;
//
//public class LinkeRow<T> implements Iterable<T> {
//
//	protected Class<T> elementClass;
//	protected int initialCapacity;
//	protected int extendsBy;
//
//	protected int[] hashCodes;
//	protected T[] values;
//	protected int size;
//
//	@SuppressWarnings("unchecked")
//	public LinkeRow(final Class<T> elementClass, final int initialCapacity, final int extendsBy) {
//		this.elementClass = elementClass;
//		this.initialCapacity = initialCapacity;
//		this.extendsBy = extendsBy;
//
//		this.hashCodes = new int[initialCapacity];
//		this.values = (T[]) Array.newInstance(elementClass, initialCapacity);
//		this.size = 0;
//	}
//
//	public void add(final int keyCode, final T value) {
//		if (size == hashCodes.length) {
//			hashCodes = Arrays.copyOf(hashCodes, size + extendsBy);
//			values = Arrays.copyOf(values, size + extendsBy);
//		}
//		this.hashCodes[size] = keyCode;
//		this.values[size] = value;
//		size++;
//	}
//
//	public T get(final int hashCode) {
//		for (int i = 0; i < size; i++) {
//			if (hashCode == hashCodes[i]) {
//				return values[i];
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public String toString() {
//		return Arrays.toString(values);
//	}
//
//	public Iterator<T> iterator() {
//		return new Iterator<T>() {
//
//			protected T[] vs = values;
//			protected int i = 0;
//
//			public boolean hasNext() {
//				return i < vs.length;
//			}
//
//			public T next() {
//				return vs[i++];
//			}
//
//			public void remove() {
//				// TODO Auto-generated method stub
//
//			}
//		};
//	}
//
//	public void clear() {
//		this.size = 0;
//	}
//
//	public int getSize() {
//		return size;
//	}
//
// }
