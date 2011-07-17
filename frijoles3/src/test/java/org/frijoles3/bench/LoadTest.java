package org.frijoles3.bench;

import org.frijoles3.FactoryBuilder;
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

	protected final int TEST_ITERATIONS = 100;

	@Override
	@Test
	public void testSpringInjection() throws Exception {

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
	public void testFrijolesInjection() throws Exception {

		final long t1 = System.currentTimeMillis();
		for (int i = 0; i < TEST_ITERATIONS; i++) {
			final IFrijolesFactory ctx = FactoryBuilder.build(FrijolesFactory.class);
			ctx.getColorsMap(null);
		}
		LoadTest.frijolesTime = System.currentTimeMillis() - t1;
	}

	@AfterClass
	public static void afterClass() throws Exception {

		final long minTime = (long) (Math.min(frijolesTime, springTime) / 10.0);

		final long nFrijolesTime = frijolesTime / minTime;
		final long nSpringTime = springTime / minTime;

		LOG.info("Frijoles:" + frijolesTime + "ms/Spring:" + springTime + "ms (" + +nFrijolesTime + ":"
				+ nSpringTime + ") (1:" + springTime / frijolesTime + ")");
	}

}
