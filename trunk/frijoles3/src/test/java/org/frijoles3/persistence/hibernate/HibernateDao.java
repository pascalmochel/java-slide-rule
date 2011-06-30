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
public class HibernateDao<T, TID extends Serializable> {

	// /**
	// * Factoria de beans de la sessió
	// */
	// private SessionFactory sessionFactory;

	/**
	 * Classe que es fa persistent
	 */
	private final Class<T> persistentClass;

	/**
	 * Constructor de la classe
	 */
	@SuppressWarnings("unchecked")
	public HibernateDao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	/**
	 * Retorna la classe persistent
	 * 
	 * @return Classe persistent
	 */
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	// public SessionFactory getSessionFactory() {
	// return sessionFactory;
	// }

	// public void setSessionFactory(final SessionFactory sessionFactory) {
	// this.sessionFactory = sessionFactory;
	// }

	public void delete(final T objecte) {
		HibernateSessionFactory.getSession().delete(objecte);
	}

	public void store(final T objecte) {
		HibernateSessionFactory.getSession().saveOrUpdate(objecte);
	}

	@SuppressWarnings("unchecked")
	public T findById(final TID id) {
		return (T) HibernateSessionFactory.getSession().get(getPersistentClass(), id);
	}

	@SuppressWarnings("unchecked")
	public List findAll() {
		return findByCriteria();
	}

	/**
	 * versió de <tt>findAll()</tt>, que retorna els resultats ordenats segons
	 * un criteri d'ordenació especificat
	 * 
	 * @see cat.base.baseframe.dao.IBaseDao#findAll(org.hibernate.criterion.Order)
	 */
	@SuppressWarnings("unchecked")
	public List findAll(final Order orderPredicate) {
		return findByCriteria(orderPredicate);
	}

	@SuppressWarnings("unchecked")
	protected List findByCriteria(final Criterion... criterion) {
		final Criteria crit = HibernateSessionFactory.getSession().createCriteria(getPersistentClass());
		for (final Criterion c : criterion) {
			if (c != null) {
				crit.add(c);
			}
		}
		return crit.list();
	}

	/**
	 * cerca sota un nombre variable de criteris de cerca, i ordena els
	 * resultats segons un criteri d'ordenació.
	 * 
	 * @param orderPredicate criteri d'ordenació
	 * @param criterion criteris de cerca
	 * @return la llista ordenada de resultats
	 */
	@SuppressWarnings("unchecked")
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
