package org.mb.record.config;
//package org.mb.record.config;
//
//import org.mb.record.field.Clonable;
//import org.mb.record.field.Field;
//import org.mb.record.field.compo.OneToMany;
//import org.mb.record.field.identity.IdentityGenerator;
//
//public class Config implements Clonable<Config> {
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
//	public Config doClone() {
//		// TODO fer un deep clone dels arrays de fields clonables!
//		return null;
//	}
//
//}