package org.morm.criteria;

import static org.morm.criteria.Criteria.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.morm.criteria.impl.Order;
import org.morm.record.field.impl.FObject;
import org.morm.test.Dog;

public class CriteriaTest {

	@Test
	public void testname() throws Exception {

		FObject a = new FObject("A");
		FObject b = new FObject("B");

		Criterion c1 = where(all());
		assertEquals(" WHERE 1=1 -- []", c1.renderQuery().toString());

		Criterion c2 = where(or(eq(a, 5), lt(b, 100)));
		assertEquals(" WHERE (A=? OR B<?) -- [5, 100]", c2.renderQuery().toString());

		Criterion c3 = where(not(and(eq(a, 5), lt(b, 100))));
		assertEquals(" WHERE NOT((A=? AND B<?)) -- [5, 100]", c3.renderQuery().toString());

		Criterion c4 = where(or(in(a, 1, 2, 3), isNull(b)));
		assertEquals(" WHERE (A IN (?,?,?) OR B IS NULL) -- [1, 2, 3]", c4.renderQuery().toString());

		Criterion c5 = where(and(between(a, 5, 9), isNotNull(b)));
		assertEquals(" WHERE ((A BETWEEN ? AND ?) AND B IS NOT NULL) -- [5, 9]", c5.renderQuery().toString());

		Criterion c6 = where(or(like(Dog.name, "%a%"), like(Dog.name, "%b%")));
		assertEquals(" WHERE (NAME like ? OR NAME like ?) -- [%a%, %b%]", c6.renderQuery().toString());

		Criterion c7 = concate(new Criterion[] {
		/**/where(or(like(Dog.name, "%a%"), like(Dog.name, "%b%"))),
		/**/orderBy(Order.asc(Dog.name), Order.desc(Dog.age))
		/**/});
		assertEquals(" WHERE (NAME like ? OR NAME like ?) ORDER BY NAME ASC,AGE DESC -- [%a%, %b%]",
		/**/c7.renderQuery().toString());

	}

}
