//package org.orm.record.config;
//
//import org.orm.record.field.Field;
//import org.orm.record.field.compo.OneToMany;
//import org.orm.record.field.identity.IdentityGenerator;
//
//public class Config {
//
//	public final String tableName;
//	public final IdentityGenerator<?> idField;
//	public final Field<?>[] fields;
//	public final int[] manyToOnesFieldIndexes;
//	public final OneToMany<?, ?>[] oneToManies;
//
//	public Config(final String tableName, final IdentityGenerator<?> idField, final Field<?>[] fields,
//			final int[] manyToOnesFieldIndexes, final OneToMany<?, ?>[] oneToManies) {
//		super();
//		this.tableName = tableName;
//		this.idField = idField;
//		this.fields = fields;
//		this.manyToOnesFieldIndexes = manyToOnesFieldIndexes;
//		this.oneToManies = oneToManies;
//	}
//
//	// public Config(final String tableName, final IdentityGenerator<?> idField,
//	// final Map<String, Field<?>> fields, final Set<ManyToOne<?, ?>>
//	// manyToOnes,
//	// final Map<String, OneToMany<?, ?>> oneToManies) {
//	//
//	// this.tableName = tableName;
//	// this.idField = idField;
//	//
//	// int fieldIndex = 0;
//	// final int nfields = fields.size() + manyToOnes.size();
//	// this.fields = new Field<?>[nfields];
//	// for (final Field<?> f : fields.values()) {
//	// // f.setFieldIndex(fieldIndex);
//	// this.fields[fieldIndex] = f;
//	// fieldIndex++;
//	// }
//	// int manyToOneIndex = 0;
//	// this.manyToOnesFieldIndexes = new int[manyToOnes.size()];
//	// for (final Field<?> f : manyToOnes) {
//	// // f.setFieldIndex(fieldIndex);
//	// this.fields[fieldIndex] = f;
//	// this.manyToOnesFieldIndexes[manyToOneIndex] = fieldIndex;
//	// fieldIndex++;
//	// manyToOneIndex++;
//	// }
//	// this.oneToManies = oneToManies.values().toArray(new OneToMany<?,
//	// ?>[oneToManies.size()]);
//	// }
//
// }