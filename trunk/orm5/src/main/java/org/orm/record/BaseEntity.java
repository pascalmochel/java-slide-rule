package org.orm.record;

import org.orm.exception.OrmException;
import org.orm.record.compo.ManyToOne;
import org.orm.record.compo.OneToMany;
import org.orm.record.field.Field;
import org.orm.record.field.FieldDef;
import org.orm.record.field.impl.FEnum;
import org.orm.record.identity.IdentityGenerator;

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

	// FIXME implementar array única de fields en comptes de tanta Collection,
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
			throw new OrmException("identity field yet defined, with " + getClass().getName() + "#"
					+ idField.getColumnName());
		}

		final IdentityGenerator<?> id = idField.doCloneId();
		this.idField = id;

		if (this.fields.containsKey(id.getColumnName())) {
			throw new OrmException("duplicated column name: " + getClass().getName() + "#"
					+ id.getColumnName());
		}
		this.fields.put(id.getColumnName(), id);
	}

	protected void registerFields(final FieldDef<?>... fs) {
		for (final FieldDef<?> f : fs) {
			if (this.fields.containsKey(f.getColumnName())) {
				throw new OrmException("duplicated column name: " + getClass().getName() + "#"
						+ f.getColumnName());
			}
			this.fields.put(f.getColumnName(), f.doClone());
		}
	}

	protected void registerManyToOnes(final ManyToOne<?, ?>... manyToOnes) {
		for (final ManyToOne<?, ?> manyToOne : manyToOnes) {
			final ManyToOne<?, ?> c = manyToOne.doCloneCollaboration();
			this.manyToOnes.add(c);
			if (this.fields.containsKey(c.getColumnName())) {
				throw new OrmException("duplicated column name: " + getClass().getName() + "#"
						+ c.getColumnName());
			}
			this.fields.put(c.getColumnName(), c);
		}
	}

	protected <TID> void registerOneToMany(final OneToMany<TID, ?> oneToMany) {
		final IdentityGenerator<TID> idField = getIdField();
		oneToMany.setSelfIdFieldRef(idField);// FIXME està assignat a un
		// estàtic! Argh!
		final OneToMany<TID, ?> c = oneToMany.doClone();
		c.setSelfIdFieldRef(idField);// FIXME urgent!
		if (this.oneToManies.containsKey(c.getColumnName())) {
			throw new OrmException("duplicated column name: " + getClass().getName() + "#"
					+ c.getColumnName());
		}
		this.oneToManies.put(c.getColumnName(), c);
	}

	@SuppressWarnings("unchecked")
	protected <T> void set(final FieldDef<T> fieldDef, final T value) {
		final Field<T> self = (Field<T>) fields.get(fieldDef.getColumnName());
		if (self == null) {
			throw new OrmException(fieldDef.getColumnName() + "=" + fieldDef + " not found in: " + fields);
		}
		self.setValue(value);
	}

	@SuppressWarnings("unchecked")
	protected <T> T get(final FieldDef<T> fieldDef) {
		final Field<T> self = (Field<T>) fields.get(fieldDef.getColumnName());
		if (self == null) {
			throw new OrmException(fieldDef.getColumnName() + "=" + fieldDef + " not found in: " + fields);
		}
		return self.getValue();
	}

	@SuppressWarnings("unchecked")
	protected <T extends Enum<T>> T getEnum(final FEnum<T> enumField) {
		final FEnum<T> self = (FEnum<T>) fields.get(enumField.getColumnName());
		if (self == null) {
			throw new OrmException(enumField.getColumnName() + "=" + enumField + " not found in: " + fields);
		}
		return self.getEnumValue();
	}

	@SuppressWarnings("unchecked")
	protected <T extends Enum<T>> void setEnum(final FEnum<T> enumField, final T value) {
		final FEnum<T> self = (FEnum<T>) fields.get(enumField.getColumnName());
		if (self == null) {
			throw new OrmException(enumField.getColumnName() + "=" + enumField + " not found in: " + fields);
		}
		self.setEnumValue(value);
	}

	@SuppressWarnings("unchecked")
	protected <TID, E extends Entity> void setCollaboration(final ManyToOne<TID, E> manyToOneField,
			final E value) {
		final ManyToOne<TID, E> self = (ManyToOne<TID, E>) fields.get(manyToOneField.getColumnName());
		if (self == null) {
			throw new OrmException(manyToOneField.getColumnName() + "=" + manyToOneField + " not found in: "
					+ manyToOnes);
		}
		self.setCollaboration(value);
	}

	@SuppressWarnings("unchecked")
	protected <TID, E extends Entity> E getCollaboration(final ManyToOne<TID, E> manyToOneField) {
		final ManyToOne<TID, E> self = (ManyToOne<TID, E>) fields.get(manyToOneField.getColumnName());
		if (self == null) {
			throw new OrmException(manyToOneField.getColumnName() + "=" + manyToOneField + " not found in: "
					+ manyToOnes);
		}
		return self.getCollaboration();
	}

	@SuppressWarnings("unchecked")
	protected <E extends Entity> void setCollaboration(final OneToMany<?, E> collaborableField,
			final List<E> value) {
		final OneToMany<?, E> self = (OneToMany<?, E>) oneToManies.get(collaborableField.getColumnName());
		if (self == null) {
			throw new OrmException(collaborableField.getColumnName() + "=" + collaborableField
					+ " not found in: " + oneToManies);
		}
		self.setCollaboration(value);
	}

	@SuppressWarnings("unchecked")
	protected <E extends Entity> List<E> getCollaboration(final OneToMany<?, E> collaborableField) {
		final OneToMany<?, E> self = (OneToMany<?, E>) oneToManies.get(collaborableField.getColumnName());
		if (self == null) {
			throw new OrmException(collaborableField.getColumnName() + "=" + collaborableField
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
