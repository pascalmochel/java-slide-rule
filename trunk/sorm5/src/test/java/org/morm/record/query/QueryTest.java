//package org.morm.record.query;
//
//import org.junit.Test;
//import org.morm.record.Rabbit;
//
//import static org.junit.Assert.*;
//import static org.morm.criteria.FieldRestrictions.*;
//
//public class QueryTest {
//
//	@Test
//	public void testname() throws Exception {
//		assertEquals(//
//				/**/"SELECT * FROM RABBIT WHERE (NAME=? AND ID_DOG=?) -- [[arb, 2]]",
//				/**/Select.from(Rabbit.class).where(and(eq(Rabbit.name, "arb"), eq(Rabbit.id, 2)))
//						.toString()
//		/**/);
//	}
// }
