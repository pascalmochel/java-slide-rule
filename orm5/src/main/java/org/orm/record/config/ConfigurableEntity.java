//package org.orm.record.config;
//
//import org.orm.record.field.Field;
//import org.orm.record.field.FieldDef;
//import org.orm.record.field.compo.ManyToOne;
//import org.orm.record.field.compo.OneToMany;
//import org.orm.record.field.identity.IdentityGenerator;
//
//import java.util.Arrays;
//
//public class ConfigurableEntity implements IConfigurableEntity {
//
//	protected String tableName;
//	protected IdentityGenerator<?> idField;
//	protected Field<?>[] fs;
//	protected ManyToOne<?, ?>[] manyToOnes;
//	protected OneToMany<?, ?>[] oneToManies;
//
//	public IConfigurableEntity setTableName(final String tableName) {
//		this.tableName = tableName;
//		return this;
//	}
//
//	public IConfigurableEntity registerIdField(final IdentityGenerator<?> idField) {
//		this.idField = idField;
//		return this;
//	}
//
//	public IConfigurableEntity registerFields(final Field<?>... fs) {
//		this.fs = fs;
//		return this;
//	}
//
//	public IConfigurableEntity registerManyToOnes(final ManyToOne<?, ?>... manyToOnes) {
//		this.manyToOnes = manyToOnes;
//		return this;
//	}
//
//	public IConfigurableEntity registerOneToMany(final OneToMany<?, ?>... oneToManies) {
//		this.oneToManies = oneToManies;
//		return this;
//	}
//
//	public Config configure() {
//		final Field<?>[] fields = cons(idField, concate(fs, manyToOnes));
//		final int[] manyToOneFieldIndexes = new int[manyToOnes.length];
//		for (int i = 0; i < manyToOnes.length; i++) {
//
//		}
//		return this;
//	}
//
//	// TODO ubicar
//	public static <T> T[] cons(final T element, final T[] array) {
//		final T[] r = Arrays.copyOf(array, array.length + 1);
//		r[0] = element;
//		System.arraycopy(array, 0, r, 1, array.length);
//		return r;
//	}
//
//	// TODO ubicar
//	public static <T> T[] append(final T[] array, final T element) {
//		final T[] r = Arrays.copyOf(array, array.length + 1);
//		r[array.length] = element;
//		return r;
//	}
//
//	// TODO ubicar
//	public static <T> T[] concate(final T[] array1, final T[] array2) {
//		final T[] r = Arrays.copyOf(array1, array1.length + array2.length);
//		System.arraycopy(array2, 0, r, array1.length, array2.length);
//		return r;
//	}
//
// }