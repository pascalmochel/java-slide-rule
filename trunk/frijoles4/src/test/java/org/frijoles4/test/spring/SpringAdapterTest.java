package org.frijoles4.test.spring;

import static org.junit.Assert.*;

import org.frijoles4.FrijolesContext;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class SpringAdapterTest {

	@Test
	public void testname() throws Exception {

		final ClassPathResource resource = new ClassPathResource("spring-adapter-context.xml");
		final BeanFactory springFactory = new XmlBeanFactory(resource);

		final FrijolesContext frijolesfactory = (FrijolesContext) springFactory.getBean("frijoles-factory");

		assertTrue(frijolesfactory.getBean("prototype-blue-color") != frijolesfactory
				.getBean("prototype-blue-color"));
		assertTrue(frijolesfactory.getBean("single-red-color") == frijolesfactory.getBean("single-red-color"));

		assertEquals("[prototype-blue-color={prototype}, single-red-color={singleton}]", frijolesfactory
				.toString());
	}

}
