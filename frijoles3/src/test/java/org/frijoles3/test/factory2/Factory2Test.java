package org.frijoles3.test.factory2;

import java.awt.Color;

import org.frijoles3.FactoryBuilder;
import org.frijoles3.test.ents.Dog;
import org.frijoles3.test.ents.Person;
import org.frijoles3.test.factory2.factories.FF;
import org.frijoles3.test.factory2.factories.IFF;
import org.junit.Test;

import static org.junit.Assert.*;

public class Factory2Test {

	@Test
	public void testname() throws Exception {

		final IFF f2 = FactoryBuilder.build(FF.class);

		final Dog dog = f2.getAppFactory(null).getDog(null);
		final Person person = f2.getAppFactory(null).getPerson(null);

		assertTrue(dog == person.getDog());
		assertEquals("PersonImpl [dog=DogImpl [name=din], name=mhc]", person.toString());

		//

		final Color color = f2.getBussinesFactory(null).getColor(null);

		assertEquals("java.awt.Color[r=0,g=0,b=1]", color.toString());
	}

}
