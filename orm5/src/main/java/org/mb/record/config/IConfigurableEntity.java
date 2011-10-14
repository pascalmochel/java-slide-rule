package org.mb.record.config;
//package org.mb.record.config;
//
//import org.mb.record.field.Field;
//import org.mb.record.field.compo.ManyToOne;
//import org.mb.record.field.compo.OneToMany;
//import org.mb.record.field.identity.IdentityGenerator;
//
//public interface IConfigurableEntity {
//
//	IConfigurableEntity setTableName(final String tableName);
//
//	IConfigurableEntity registerIdField(final IdentityGenerator<?> idField);
//
//	IConfigurableEntity registerFields(final Field<?>... fs);
//
//	IConfigurableEntity registerManyToOnes(final ManyToOne<?, ?>... manyToOnes);
//
//	IConfigurableEntity registerOneToMany(final OneToMany<?, ?>... oneToManies);
//
//	Config configure();
//
// }