/**
 * BASEFrame : Marc de desenvolupament J2EE desenvolupat per
 * Linecom Networks per a BASE Gestió d'Ingressos
 *
 * Març 2009
 */
package org.frijoles3.persistence.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * Classe genèrica que implementa les funcions DAO més tipiques que es puguin
 * necessitar per a desenvolupar un CRUD
 * 
 * @author jfabre
 * @param <T> Tipus de dada de la entitat
 * @param <TID> Tipus de dada de l'identificador que utilitza la entitat
 */
@SuppressWarnings("unchecked")
public class HibernateDao<T, TID extends Serializable> {

	private final Class<T> persistentClass;

	public HibernateDao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public void delete(final T objecte) {
		HibernateSessionFactory.getSession().delete(objecte);
	}

	public void store(final T objecte) {
		HibernateSessionFactory.getSession().saveOrUpdate(objecte);
	}

	public T findById(final TID id) {
		return (T) HibernateSessionFactory.getSession().get(getPersistentClass(), id);
	}

	public List findAll() {
		return findByCriteria();
	}

	public List findAll(final Order orderPredicate) {
		return findByCriteria(orderPredicate);
	}

	protected List findByCriteria(final Criterion... criterion) {
		final Criteria crit = HibernateSessionFactory.getSession().createCriteria(getPersistentClass());
		for (final Criterion c : criterion) {
			if (c != null) {
				crit.add(c);
			}
		}
		return crit.list();
	}

	protected List findByCriteria(final Order orderPredicate, final Criterion... criterion) {
		final Criteria crit = HibernateSessionFactory.getSession().createCriteria(getPersistentClass());
		for (final Criterion c : criterion) {
			if (c != null) {
				crit.add(c);
			}
		}
		return crit.addOrder(orderPredicate).list();
	}

}
