//package org.morm.criteria.impl;
//
//import org.morm.criteria.Criterion;
//import org.morm.record.QueryObject;
//
//public class CriterionList implements Criterion {
//
//	protected final Criterion[] queries;
//
//	public CriterionList(final Criterion[] criterions) {
//		super();
//		this.queries = criterions.clone();
//	}
//
//	public QueryObject renderQuery() {
//		final QueryObject r = new QueryObject();
//		for (final Criterion q : queries) {
//			r.append(q.renderQuery());
//		}
//		return r;
//	}
//
//}
