package org.orm.record.field.identity.impl.hsqldb;

import org.orm.exception.OrmException;
import org.orm.mapper.DataMapper;
import org.orm.query.IQueryObject;
import org.orm.query.QueryObject;
import org.orm.record.field.IdentifiableField;
import org.orm.record.field.identity.IdentityGenerator;

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
