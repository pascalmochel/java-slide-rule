package org.frijoles3.bench;

import org.frijoles3.FactoryBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * <h2>INFO: Frijoles:424ms/Spring:8790ms (1:20)</h2>
 * <p>
 * <ul>
 * <li>3 sec ~ 1 min
 * </ul>
 * 
 * @author mhoms
 */
public class BenchMarkTest {

	protected static final Logger LOG = Logger.getLogger(BenchMarkTest.class.getName());

	protected final int TEST_ITERATIONS = 10000;

	protected static long springTime;
	protected static long frijolesTime;

	public BenchMarkTest() {
		super();
	}

	@Before
	public void before() {
		System.gc();
	}

	@Test
	public void testSpringInjection() throws Exception {
		final ClassPathResource resource = new ClassPathResource("spring-benchmark-context.xml");
		final BeanFactory factory = new XmlBeanFactory(resource);

		final ColorsMap colorsMap = (ColorsMap) factory.getBean("colorsMap");
		assertColorsMap(colorsMap);

		final long t1 = System.currentTimeMillis();
		for (int i = 0; i < TEST_ITERATIONS; i++) {
			factory.getBean("colorsMap");
		}
		BenchMarkTest.springTime = System.currentTimeMillis() - t1;
	}

	@Test
	public void testFrijolesInjection() throws Exception {

		final IFrijolesFactory ctx = FactoryBuilder.build(FrijolesFactory.class);

		final ColorsMap colorsMap = ctx.getColorsMap(null);
		assertColorsMap(colorsMap);

		final long t1 = System.currentTimeMillis();
		for (int i = 0; i < TEST_ITERATIONS; i++) {
			ctx.getColorsMap(null);
		}
		BenchMarkTest.frijolesTime = System.currentTimeMillis() - t1;
	}

	private void assertColorsMap(final ColorsMap colorsMap) {

		assertNotNull(colorsMap);
		assertNotNull(colorsMap.getColors());
		assertEquals(3, colorsMap.getColors().size());

		assertNotNull(colorsMap.getColors().get("red"));
		assertEquals(255, colorsMap.getColors().get("red").getRed());
		assertEquals(0, colorsMap.getColors().get("red").getGreen());
		assertEquals(0, colorsMap.getColors().get("red").getBlue());

		assertNotNull(colorsMap.getColors().get("green"));
		assertEquals(0, colorsMap.getColors().get("green").getRed());
		assertEquals(255, colorsMap.getColors().get("green").getGreen());
		assertEquals(0, colorsMap.getColors().get("green").getBlue());

		assertNotNull(colorsMap.getColors().get("blue"));
		assertEquals(0, colorsMap.getColors().get("blue").getRed());
		assertEquals(0, colorsMap.getColors().get("blue").getGreen());
		assertEquals(255, colorsMap.getColors().get("blue").getBlue());
	}

	@AfterClass
	public static void afterClass() throws Exception {
		final long minTime = Math.min(frijolesTime, springTime);
		final long nFrijolesTime = frijolesTime / minTime;
		final long nSpringTime = springTime / minTime;
		LOG.info("Frijoles:" + frijolesTime + "ms/Spring:" + springTime + "ms (" + nFrijolesTime + ":"
				+ nSpringTime + ")");
	}

}
