/**
 * BASEFrame : Marc de desenvolupament J2EE desenvolupat per
 * Linecom Networks per a BASE Gestió d'Ingressos
 *
 * Març 2009
 *
 */

package org.orm.bo;

import java.util.List;

import org.orm.criteria.Criteria;
import org.orm.record.Entity;
import org.orm.record.field.Field;

/**
 * Classe genèrica que implementa les funcions DAO més tipiques que es puguin
 * necessitar per a desenvolupar un CRUD
 * 
 * @param <T> Tipus de dada de la entitat
 * @param <TID> Tipus de dada de l'identificador que utilitza la entitat
 */
public interface IGenericDao<T extends Entity, TID> {

	void delete(final T entity);

	void store(final T entity);

	T loadById(final TID id);

	<V> List<T> loadByColumn(final Field<V> field, V value);

	List<T> findAll();

	List<T> loadByCriteria(final Criteria criteria);

	T loadUniqueByCriteria(final Criteria criteria);

}
