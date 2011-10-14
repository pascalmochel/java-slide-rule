//package collection;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
//
//import org.junit.Test;
//import org.orm.collection.H;
//import org.orm.collection.M;
//import org.orm.record.field.Field;
//import org.orm.record.field.identity.impl.hsqldb.HsqldbIdentity;
//import org.orm.record.field.regular.FString;
//import org.orm.record.field.regular.primitive.FInteger;
//import org.orm.record.field.regular.primitive.FLong;
//
//public class LTest2 {
//
//	@Test
//	public void testname2() throws Exception {
//
//		final H<String> h = new H<String>(String.class, 4, 3);
//
//		h.put(1, "un");
//		h.put(2, "dos");
//		h.put(3, "tres");
//		h.put(4, "quatre");
//		h.put(5, "cinc");
//		h.put(6, "sis");
//
//		assertEquals("[0:[quatre], 1:[un, cinc], 2:[dos, sis], 3:[tres], )", h.toString());
//
//		assertEquals("quatre", h.get(4));
//		assertEquals("sis", h.get(6));
//		assertNull(h.get(7));
//
//		// assertEquals("[un, dos, tres, quatre, cinc, sis]",
//		// h.
//
//		h.put(4, "quatre2");
//		assertEquals("[0:[quatre, quatre2], 1:[un, cinc], 2:[dos, sis], 3:[tres], )", h.toString());
//		assertEquals("quatre", h.get(4));
//	}
//
//	@SuppressWarnings( { "unchecked", "deprecation" })
//	public static void main(String[] args) {
//
//		Class<Field> class1 = Field.class;
//		M<String, Field> fs = new M<String, Field>(class1);
//
//		fs.put("ID", new HsqldbIdentity<Long>(new FLong("ID")));
//		fs.put("NAME", new FString("NAME"));
//		fs.put("AGE", new FInteger("AGE"));
//
//		assertEquals("ID", fs.get("ID").getColumnName());
//		assertEquals("NAME", fs.get("NAME").getColumnName());
//		assertEquals("AGE", fs.get("AGE").getColumnName());
//
//		assertEquals("[ID=null, NAME=null, AGE=null]", fs.values().toString());
//	}
//
//}
