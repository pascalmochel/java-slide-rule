package org.orm.bo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.orm.bo.service.IService;
import org.orm.test.ent.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test-context.xml")
public class SpringBoTest extends AbstractBoTest {

	@Autowired
	@Qualifier("service")
	public IService txService;

	@Test
	public void testname() throws Exception {

		Dog dog = txService.loadDogById(101);
		assertEquals("[ID_DOG=101, NAME=d2, AGE=2, [...]]", dog.toString());
		dog.getRabbits();
		assertEquals(
		/**/"[ID_DOG=101, NAME=d2, AGE=2, " +
		/**/"[[ID_RABBIT=202, NAME=c21, AGE=2, NUM_DOG=101=>[...]], " +
		/**/"[ID_RABBIT=203, NAME=c22, AGE=2, NUM_DOG=101=>[...]]]]",
		/**/dog.toString());

		dog.getRabbits().get(0).setAge(1000);
		txService.storeDog(dog);

		dog = txService.loadDogById(101);
		assertEquals("[ID_DOG=101, NAME=d2, AGE=2, [...]]", dog.toString());
		dog.getRabbits();
		assertEquals(
		/**/"[ID_DOG=101, NAME=d2, AGE=2, " +
		/**/"[[ID_RABBIT=202, NAME=c21, AGE=1000, NUM_DOG=101=>[...]], " +
		/**/"[ID_RABBIT=203, NAME=c22, AGE=2, NUM_DOG=101=>[...]]]]",
		/**/dog.toString());
	}

}
