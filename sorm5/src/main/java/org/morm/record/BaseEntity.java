package org.morm.record;

import org.morm.exception.SormException;
import org.morm.record.compo.ManyToOne;
import org.morm.record.compo.OneToMany;
import org.morm.record.field.Field;
import org.morm.record.field.FieldDef;
import org.morm.record.field.impl.FEnum;
import org.morm.record.identity.IdentityGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class BaseEntity {

	protected Logger log = Logger.getLogger(getClass().getName());

	private String tableName;

	private IdentityGenerator<?> idField;
	private final Map<String, Field<?>> fields;
	private final Set<ManyToOne<?, ?>> manyToOnes;
	private final Map<String, OneToMany<?, ?>> oneToManies;

	public BaseEntity() {
		super();
		this.tableName = getClass().getSimpleName().toUpperCase();
		this.fields = new LinkedHashMap<String, Field<?>>();
		this.manyToOnes = new HashSet<ManyToOne<?, ?>>();
		this.oneToManies = new HashMap<String, OneToMany<?, ?>>();
	}

	protected void setTableName(final String tableName) {
		this.tableName = tableName;
	}

	protected void registerIdField(final IdentityGenerator<?> idField) {
		if (this.idField != null) {
			throw new SormException("identity field yet defined, with " + getClass().getName() + "#"
					+ idField.getColumnName());
		}

		final IdentityGenerator<?> id = idField.doCloneId();
		this.idField = id;

		if (this.fields.containsKey(id.getColumnName())) {
			throw new SormException("duplicated column name: " + getClass().getName() + "#"
					+ id.getColumnName());
		}
		this.fields.put(id.getColumnName(), id);
	}

	protected void registerFields(final FieldDef<?>... fs) {
		for (final FieldDef<?> f : fs) {
			if (this.fields.containsKey(f.getColumnName())) {
				throw new SormException("duplicated column name: " + getClass().getName() + "#"
						+ f.getColumnName());
			}
			this.fields.put(f.getColumnName(), f.doClone());
		}
	}

	protected void registerManyToOnes(final ManyToOne<?, ?>... manyToOnes) {
		for (ManyToOne<?, ?> manyToOne : manyToOnes) {
			final ManyToOne<?, ?> c = manyToOne.doCloneCollaboration();
			this.manyToOnes.add(c);
			if (this.fields.containsKey(c.getColumnName())) {
				throw new SormException("duplicated column name: " + getClass().getName() + "#"
						+ c.getColumnName());
			}
			this.fields.put(c.getColumnName(), c);
		}
	}

	protected <TID> void registerOneToMany(final OneToMany<TID, ?> oneToMany) {
		final IdentityGenerator<TID> idField = getIdField();
		oneToMany.setSelfIdFieldRef(idField);
		final OneToMany<TID, ?> c = oneToMany.doClone();
		if (this.oneToManies.containsKey(c.getColumnName())) {
			throw new SormException("duplicated column name: " + getClass().getName() + "#"
					+ c.getColumnName());
		}
		this.oneToManies.put(c.getColumnName(), c);
	}

	@SuppressWarnings("unchecked")
	protected <T> void set(final FieldDef<T> fieldDef, final T value) {
		final Field<T> self = (Field<T>) fields.get(fieldDef.getColumnName());
		self.setValue(value);
	}

	@SuppressWarnings("unchecked")
	protected <T> T get(final FieldDef<T> fieldDef) {
		final Field<T> self = (Field<T>) fields.get(fieldDef.getColumnName());
		return self.getValue();
	}

	@SuppressWarnings("unchecked")
	protected <T extends Enum<T>> T getEnum(final FEnum<T> enumField) {
		final FEnum<T> self = (FEnum<T>) fields.get(enumField.getColumnName());
		return self.getEnumValue();
	}

	@SuppressWarnings("unchecked")
	protected <T extends Enum<T>> void setEnum(final FEnum<T> enumField, final T value) {
		final FEnum<T> self = (FEnum<T>) fields.get(enumField.getColumnName());
		self.setEnumValue(value);
	}

	@SuppressWarnings("unchecked")
	protected <TID, E extends Entity> void setCollaboration(final ManyToOne<TID, E> manyToOneField,
			final E value) {
		final ManyToOne<TID, E> self = (ManyToOne<TID, E>) fields.get(manyToOneField.getColumnName());
		self.setCollaboration(value);
	}

	@SuppressWarnings("unchecked")
	protected <TID, E extends Entity> E getCollaboration(final ManyToOne<TID, E> manyToOneField) {
		final ManyToOne<TID, E> self = (ManyToOne<TID, E>) fields.get(manyToOneField.getColumnName());
		return self.getCollaboration();
	}

	@SuppressWarnings("unchecked")
	protected <E extends Entity> void setCollaboration(final OneToMany<?, E> collaborableField,
			final List<E> value) {
		final OneToMany<?, E> self = (OneToMany<?, E>) oneToManies.get(collaborableField.getColumnName());
		self.setCollaboration(value);
	}

	@SuppressWarnings("unchecked")
	protected <E extends Entity> List<E> getCollaboration(final OneToMany<?, E> collaborableField) {
		final OneToMany<?, E> self = (OneToMany<?, E>) oneToManies.get(collaborableField.getColumnName());
		// FIXME
		if (self == null) {
			System.out.println(collaborableField + "/" + collaborableField.getColumnName()
					+ " not found in: " + oneToManies);
		}
		return self.getCollaboration();
	}

	public String getTableName() {
		return tableName;
	}

	@SuppressWarnings("unchecked")
	public <T> IdentityGenerator<T> getIdField() {
		return (IdentityGenerator<T>) idField;
	}

	public Collection<Field<?>> getFields() {
		return fields.values();
	}

	public Set<ManyToOne<?, ?>> getManyToOnes() {
		return manyToOnes;
	}

	public Map<String, OneToMany<?, ?>> getOneToManies() {
		return oneToManies;
	}

	@Override
	public String toString() {
		final List<String> r = new ArrayList<String>();
		for (final Field<?> i : getFields()) {
			r.add(i.toString());
		}
		for (final OneToMany<?, ?> i : oneToManies.values()) {
			r.add(i.toString());
		}
		return r.toString();
	}

}
