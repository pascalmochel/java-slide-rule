package org.orm.criteria;

import org.junit.Test;
import org.orm.criteria.order.Order;
import org.orm.test.ent.Dog;

import static org.junit.Assert.*;
import static org.orm.criteria.restriction.Restriction.*;

public class CriteriaRenderTest {

	@Test
	public void testname() throws Exception {

		final Criterion c1 = all();
		assertEquals(" WHERE 1=1 -- []", c1.renderQuery().toString());

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

	// @Test
	// public void testnameGroupBy() throws Exception {
	//
	// final Criterion c = Criteria
	// /**/.select(Dog.class, Aggregate.max(Dog.age, "VALUE"))
	// /**/.where(not(eq(Dog.name, "din")))
	// /**/.groupBy(Dog.name)
	// /**/.having(lt(Dog.age, 10))
	// /**/.orderBy(Order.asc(Dog.name));
	//
	// assertEquals(
	// "SELECT MAX(AGE) AS VALUE FROM DOG WHERE NOT(NAME=?) GROUP BY NAME HAVING AGE<? ORDER BY NAME ASC -- [din, 10]",
	// /**/c.renderQuery().toString());
	// }

}
