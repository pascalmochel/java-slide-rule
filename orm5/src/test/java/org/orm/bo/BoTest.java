package org.orm.bo;

import org.junit.Test;
import org.orm.bo.dao.DogDao;
import org.orm.bo.dao.RabbitDao;
import org.orm.bo.service.IService;
import org.orm.bo.service.Service;
import org.orm.test.ent.Dog;

import static org.junit.Assert.*;

public class BoTest extends AbstractBoTest {

	@Test
	public void testname() throws Exception {

		final Service service = new Service(new DogDao(), new RabbitDao());
		final IService txService = TxInterceptor.createInstance(service);

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
