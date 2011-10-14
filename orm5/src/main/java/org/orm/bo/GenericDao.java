package org.orm.bo;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.orm.criteria.Criteria;
import org.orm.criteria.restriction.Restriction;
import org.orm.record.Entity;
import org.orm.record.field.Field;

public class GenericDao<T extends Entity, TID> implements IGenericDao<T, TID> {

	private final Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public GenericDao() {
		final ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	public void delete(final T entity) {
		entity.delete();
	}

	public List<T> findAll() {
		return Criteria.selectAll().list(entityClass);
	}

	public <V> List<T> loadByColumn(final Field<V> field, final V value) {
		return Criteria.selectBy(Restriction.eq(field, value)).list(entityClass);
	}

	public T loadById(final TID id) {
		return Criteria.selectById(entityClass, id);
	}

	public List<T> loadByCriteria(final Criteria criteria) {
		return criteria.list(entityClass);
	}

	public T loadUniqueByCriteria(final Criteria criteria) {
		return criteria.uniqueResult(entityClass);
	}

	public void store(final T entity) {
		entity.store();
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

}
