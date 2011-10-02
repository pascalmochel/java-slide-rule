package org.orm;

import org.junit.Test;
import org.orm.criteria.Criteria;
import org.orm.criteria.impl.Order;
import org.orm.criteria2.Crit;
import org.orm.query.IQueryObject;
import org.orm.test.Dog;

import static org.junit.Assert.*;

public class CTest {

	@Test
	public void testname() throws Exception {

		final IQueryObject r = new Crit().select(Dog.class).where(Criteria.eq(Dog.name, "din")).orderBy(
				Order.asc(Dog.name), Order.desc(Dog.age)).renderQuery();

		assertEquals("SELECT * FROM DOG WHERE NAME=? ORDER BY NAME ASC,AGE DESC -- [din]", r.toString());

	}

}
