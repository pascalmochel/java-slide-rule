package org.mb.record.field.identity.impl.hsqldb;

import org.mb.exception.OrmException;
import org.mb.mapper.DataMapper;
import org.mb.query.IQueryObject;
import org.mb.query.QueryObject;
import org.mb.record.field.IdentifiableField;
import org.mb.record.field.identity.IdentityGenerator;

public class HsqldbSequence<T> extends IdentityGenerator<T> {

	protected final String sequenceName;

	public HsqldbSequence(final IdentifiableField<T> field, final String sequenceName) {
		super(field);
		this.sequenceName = sequenceName;
	}

	@Override
	public IQueryObject getQuery() {
		return new QueryObject("CALL NEXT VALUE FOR " + sequenceName);
	}

	@Override
	public void setGeneratedValue() {
		try {
			DataMapper.aggregateIdentityField(field, super.query);
		} catch (final Exception e) {
			throw new OrmException(ERROR_OBTAINING_IDENTITY_KEY + query, e);
		}
	}

	public String getSequenceName() {
		return sequenceName;
	}

	@Override
	public boolean generateBefore() {
		return true;
	}

	@Override
	public IdentityGenerator<T> doClone() {
		return new HsqldbSequence<T>((IdentifiableField<T>) field.doClone(), sequenceName);
	}

}
