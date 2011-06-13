package org.frijoles3.spring;

import org.frijoles3.test.basic.ITestingFactory;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import static org.junit.Assert.*;

public class SpringAdapterTest {

	@Test
	public void testname() throws Exception {

		final ClassPathResource resource = new ClassPathResource("spring-adapter-context.xml");
		final BeanFactory springFactory = new XmlBeanFactory(resource);

		final ITestingFactory frijolesfactory = (ITestingFactory) springFactory
				.getBean("frijoles-factory-builder");

		assertTrue(frijolesfactory.getPrototypeBlueColor(null) != frijolesfactory.getPrototypeBlueColor(null));
		assertTrue(frijolesfactory.getSingleRedColor(null) == frijolesfactory.getSingleRedColor(null));

		assertEquals(
				"TestingFactory: [getPrototypeBlueColor={prototype}, getSingleRedColor={singleton}[init:true]]",
				frijolesfactory.toString());
	}

}
