package org.orm.record.identity.impl.hsqldb;

import org.orm.exception.OrmException;
import org.orm.mapper.DataMapper;
import org.orm.query.IQueryObject;
import org.orm.query.QueryObject;
import org.orm.record.field.IdentifiableField;
import org.orm.record.identity.IdentityGenerator;

public class HsqldbIdentity<T> extends IdentityGenerator<T> {

	public HsqldbIdentity(final IdentifiableField<T> field) {
		super(field);
	}

	@Override
	public IQueryObject getQuery() {
		return new QueryObject("CALL IDENTITY()");
	}

	@Override
	public void setGeneratedValue() {
		try {
			DataMapper.aggregateIdentityField(field, super.query);
		} catch (final Exception e) {
			throw new OrmException(ERROR_OBTAINING_IDENTITY_KEY + query, e);
		}
	}

	@Override
	public boolean generateBefore() {
		return false;
	}

	@Override
	public IdentityGenerator<T> doCloneId() {
		return new HsqldbIdentity<T>((IdentifiableField<T>) field.doClone());
	}

}
