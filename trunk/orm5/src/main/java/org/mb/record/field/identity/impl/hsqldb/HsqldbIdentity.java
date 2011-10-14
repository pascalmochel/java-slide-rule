package org.mb.record.field.identity.impl.hsqldb;

import org.mb.exception.OrmException;
import org.mb.mapper.DataMapper;
import org.mb.query.IQueryObject;
import org.mb.query.QueryObject;
import org.mb.record.field.IdentifiableField;
import org.mb.record.field.identity.IdentityGenerator;

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
	public IdentityGenerator<T> doClone() {
		return new HsqldbIdentity<T>((IdentifiableField<T>) field.doClone());
	}

}
