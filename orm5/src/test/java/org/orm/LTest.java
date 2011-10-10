package org.orm;

import java.awt.Color;

import org.junit.Test;
import org.orm.record.HasheMap;
import org.orm.record.LinkeRow;

import static org.junit.Assert.*;

public class LTest {

	@Test
	public void testname() throws Exception {
		final LinkeRow<Integer> l = new LinkeRow<Integer>(Integer.class, 0, 1);

		assertEquals("[]", l.toString());
		l.add(Integer.valueOf(1).hashCode(), Integer.valueOf(1));
		assertEquals("[1]", l.toString());
		l.add(Integer.valueOf(2).hashCode(), Integer.valueOf(2));
		assertEquals("[1, 2]", l.toString());

		final Integer c = l.get(Integer.valueOf(2).hashCode());
		assertEquals("2", c.toString());
	}

	@Test
	public void testname2() throws Exception {

		final HasheMap<String> h = new HasheMap<String>(String.class, 4);

		h.put(1, "un");
		h.put(2, "dos");
		h.put(3, "tres");
		h.put(4, "quatre");
		h.put(5, "cinc");
		h.put(6, "sis");

		assertEquals("[0:[quatre], 1:[un, cinc], 2:[dos, sis], 3:[tres], )", h.toString());

		assertEquals("quatre", h.get(Integer.valueOf(4).hashCode()));
		assertEquals("sis", h.get(Integer.valueOf(6).hashCode()));

		for (final String k : h) {
			System.out.println(k);
		}
	}

}
