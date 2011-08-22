package org.frijoles4.test.bench;

import org.frijoles4.FrijolesContext;
import org.junit.AfterClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * <h2>INFO: Java:1150ms/Frijoles:3361ms/Spring:108500ms (10:29:943) (1:32)</h2>
 * <p>
 * <tt>2 sec ~ 1 min</tt>
 * 
 * @author mhoms
 */
public class LoadTest extends BenchMarkTest {

	protected final int TEST_ITERATIONS = 20;

	@Override
	@Test
	public void testSpring() throws Exception {

		final long t1 = System.currentTimeMillis();
		for (int i = 0; i < TEST_ITERATIONS; i++) {
			final ClassPathResource resource = new ClassPathResource("spring-benchmark-context.xml");
			final BeanFactory factory = new XmlBeanFactory(resource);
			factory.getBean("colorsMap");
		}
		LoadTest.springTime = System.currentTimeMillis() - t1;
	}

	@Override
	@Test
	public void testFrijoles4() throws Exception {

		final long t1 = System.currentTimeMillis();
		for (int i = 0; i < TEST_ITERATIONS; i++) {
			final FrijolesContext ctx = FrijolesContext.build(Frijoles4Factory.class);
			ctx.getBean("colors-map");
		}
		LoadTest.frijoles4Time = System.currentTimeMillis() - t1;
	}

	@AfterClass
	public static void afterClass() throws Exception {

		final long minTime = (long) (Math.min(frijoles4Time, springTime) / 10.0);

		final long nFrijolesTime = frijoles4Time / minTime;
		final long nSpringTime = springTime / minTime;

		LOG.info("Frijoles:" + frijoles4Time + "ms/Spring:" + springTime + "ms (" + +nFrijolesTime + ":"
				+ nSpringTime + ") (1:" + springTime / frijoles4Time + ")");
	}

}
