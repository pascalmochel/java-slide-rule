package org.orm.criteria.impl;

import org.orm.criteria.Criterion;

import java.util.List;

public interface Finish<T> extends Criterion {

	T getUnique();

	List<T> get();

}
