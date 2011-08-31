//package org.morm.record.query;
//
//import org.morm.criteria.interf.Value0;
//import org.morm.criteria.interf.Value1;
//import org.morm.criteria.interf.ValueN;
//import org.morm.record.Entity;
//
//public class Select {
//
//	protected String tableName;
//	protected QueryObject query;
//
//	public static Select from(final Class<? extends Entity> entityClass) {
//		return new Select(entityClass);
//	}
//
//	protected Select(final Class<? extends Entity> entityClass) {
//		this.tableName = entityClass.getSimpleName().toUpperCase();
//		this.query = new QueryObject().appendQuery("SELECT * FROM ").appendQuery(tableName).appendQuery(
//				" WHERE ");
//	}
//
//	public QueryObject where(final Value0 criterion) {
//		if (criterion instanceof ValueN) {
//			query.append(criterion.renderSql(), ((ValueN) criterion).getValues());
//		} else if (criterion instanceof Value1) {
//			query.append(criterion.renderSql(), ((Value1) criterion).getValue());
//		} else {
//			query.appendQuery(criterion.renderSql());
//		}
//		return query;
//	}
//
// }