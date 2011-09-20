package org.morm.criteria;

import org.junit.Test;
import org.morm.criteria.impl.Order;
import org.morm.test.Dog;

import static org.junit.Assert.*;
import static org.morm.criteria.Criteria.*;

public class CriteriaTest {

	@Test
	public void testname() throws Exception {

		final Criterion c1 = all();
		assertEquals(" -- []", c1.renderQuery().toString());

		final Criterion c2 = where(or(eq(Dog.age, 5), lt(Dog.age, 100)));
		assertEquals(" WHERE (AGE=? OR AGE<?) -- [5, 100]", c2.renderQuery().toString());

		final Criterion c3 = where(not(and(eq(Dog.age, 5), lt(Dog.age, 100))));
		assertEquals(" WHERE NOT((AGE=? AND AGE<?)) -- [5, 100]", c3.renderQuery().toString());

		final Criterion c4 = where(or(in(Dog.age, 1, 2, 3), isNull(Dog.age)));
		assertEquals(" WHERE (AGE IN (?,?,?) OR AGE IS NULL) -- [1, 2, 3]", c4.renderQuery().toString());

		final Criterion c5 = where(and(between(Dog.age, 5, 9), isNotNull(Dog.age)));
		assertEquals(" WHERE ((AGE BETWEEN ? AND ?) AND AGE IS NOT NULL) -- [5, 9]", c5.renderQuery()
				.toString());

		final Criterion c6 = where(or(like(Dog.name, "%a%"), like(Dog.name, "%b%")));
		assertEquals(" WHERE (NAME like ? OR NAME like ?) -- [%a%, %b%]", c6.renderQuery().toString());

		final Criterion c7 = concate(new Criterion[] {
		/**/where(or(like(Dog.name, "%a%"), like(Dog.name, "%b%"))),
		/**/orderBy(Order.asc(Dog.name), Order.desc(Dog.age))
		/**/});
		assertEquals(" WHERE (NAME like ? OR NAME like ?) ORDER BY NAME ASC,AGE DESC -- [%a%, %b%]",
		/**/c7.renderQuery().toString());

		final Criterion c8 = where(or(lt(Dog.age, 1), le(Dog.age, 2), gt(Dog.age, 3), ge(Dog.age, 4), ne(
				Dog.age, 5), eq(Dog.age, 6)));
		assertEquals(" WHERE (AGE<? OR AGE<=? OR AGE>? OR AGE>=? OR AGE<>? OR AGE=?) -- [1, 2, 3, 4, 5, 6]",
		/**/c8.renderQuery().toString());

	}

}