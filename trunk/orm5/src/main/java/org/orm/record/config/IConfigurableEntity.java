//package org.orm.record.config;
//
//import org.orm.record.field.Field;
//import org.orm.record.field.compo.ManyToOne;
//import org.orm.record.field.compo.OneToMany;
//import org.orm.record.field.identity.IdentityGenerator;
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