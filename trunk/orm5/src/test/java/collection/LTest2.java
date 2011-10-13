//package collection;
//
//import org.junit.Test;
//import org.orm.collection.H;
//
//import static org.junit.Assert.*;
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
//		// Arrays.toString(h.getValues()));
//
//		h.put(4, "quatre2");
//		assertEquals("[0:[quatre, quatre2], 1:[un, cinc], 2:[dos, sis], 3:[tres], )", h.toString());
//		assertEquals("quatre", h.get(4));
//	}
//
// }
