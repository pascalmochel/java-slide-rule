package org.mb.record.config;
//package org.mb.record.config;
//
//import java.util.Arrays;
//
//import org.mb.record.field.Field;
//import org.mb.record.field.compo.ManyToOne;
//import org.mb.record.field.compo.OneToMany;
//import org.mb.record.field.identity.IdentityGenerator;
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
//		int fieldsIndex = 0;
//		final Field<?>[] fields = new Field<?>[1 + fs.length + manyToOnes.length];
//		final int[] manies = new int[manyToOnes.length];
//		fields[fieldsIndex++] = idField;
//		for (Field<?> f : fs) {
//			// f.setFieldIndex(fieldsIndex);
//			fields[fieldsIndex] = f;
//			fieldsIndex++;
//		}
//		int maniesIndex = 0;
//		for (Field<?> f : manyToOnes) {
//			// f.setFieldIndex(fieldsIndex);
//			fields[fieldsIndex] = f;
//			manies[maniesIndex] = fieldsIndex;
//			fieldsIndex++;
//			maniesIndex++;
//		}
//		OneToMany<?, ?>[] ones = new OneToMany<?, ?>[oneToManies.length];
//		for (OneToMany<?, ?> f : oneToManies) {
//			// f.setFieldIndex(fieldsIndex);
//			ones[fieldsIndex] = f;
//			fieldsIndex++;
//		}
//
//		return new Config(tableName, idField, fields, manies, ones);
//	}
//
//}