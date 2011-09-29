package org.orm.record.identity.impl;

//package org.morm.record.identity.impl;
//
//import org.morm.exception.OrmException;
//import org.morm.mapper.DataMapper;
//import org.morm.record.QueryObject;
//import org.morm.record.field.Field;
//import org.morm.record.identity.IdentityGenerator;
//
//public class GenericMaxIdentityKey<T> extends IdentityGenerator<T> {
//
//	protected final String tableName;
//
//	public GenericMaxIdentityKey(final String tableName, final Field<T> field) {
//		super(field);
//		this.tableName = tableName;
//	}
//
//	@Override
//	public QueryObject getQuery() {
//
//		return new QueryObject()
//		/**/.append("SELECT MAX(")
//		/**/.append(field.getColumnName())
//		/**/.append(")+1 AS ")
//		/**/.append(field.getColumnName())
//		/**/.append(" FROM ")
//		/**/.append(tableName)
//		/**/;
//	}
//
//	@Override
//	public void setGeneratedValue() {
//
//		try {
//
//			field.setUncheckedValue(DataMapper.aggregate(super.query));
//
//		} catch (final Exception e) {
//			throw new OrmException(ERROR_OBTAINING_IDENTITY_KEY + query, e);
//		}
//	}
//
//	@Override
//	public boolean generateBefore() {
//		return true;
//	}
//
//	@Override
//	public IdentityGenerator<T> doCloneId() {
//		return new GenericMaxIdentityKey<T>(tableName, field.doClone());
//	}
//
// }
