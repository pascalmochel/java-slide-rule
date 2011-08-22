package org.frijoles4.test.bench;

import java.awt.Color;

import org.frijoles4.FrijolesContext;
import org.junit.AfterClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * @author mhoms
 */
public class BenchMarkTest {

	protected static final Logger LOG = Logger.getLogger(BenchMarkTest.class.getName());

	protected final int TEST_ITERATIONS = 20000;

	protected static long javaTime;
	protected static long springTime;
	protected static long frijoles4Time;

	public BenchMarkTest() {
		super();
	}

	@Test
	public void testJavaInjection() throws Exception {

		assertColorsMap(getJavaColorsMap());

		final long t1 = System.currentTimeMillis();
		for (int i = 0; i < TEST_ITERATIONS; i++) {
			getJavaColorsMap();
		}
		BenchMarkTest.javaTime = System.currentTimeMillis() - t1;
	}

	private ColorsMap getJavaColorsMap() {
		final Map<String, Color> colors = new HashMap<String, Color>();
		colors.put("red", new Color(255, 0, 0));
		colors.put("green", new Color(0, 255, 0));
		colors.put("blue", new Color(0, 0, 255));
		final ColorsMap colorsMap = new ColorsMap(colors);
		return colorsMap;
	}

	@Test
	public void testSpring() throws Exception {
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
	public void testFrijoles4() throws Exception {

		final FrijolesContext ctx = FrijolesContext.build(Frijoles4Factory.class);

		final ColorsMap colorsMap = ctx.getBean("colors-map");
		assertColorsMap(colorsMap);

		final long t1 = System.currentTimeMillis();
		for (int i = 0; i < TEST_ITERATIONS; i++) {
			ctx.getBean("colors-map");
		}
		BenchMarkTest.frijoles4Time = System.currentTimeMillis() - t1;
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

		final long minTime = (long) (Math.min(Math.min(javaTime, frijoles4Time), springTime) / 10.0);

		LOG.info(
		/**/"Java:" + javaTime + "/" +
		/**/"Frijoles4:" + frijoles4Time + "/" +
		/**/"Spring:" + springTime + "/"
		/**/);

		LOG.info("Java:" + javaTime + "ms/Frijoles:" + frijoles4Time + "ms/Spring:" + springTime + "ms ("
				+ javaTime / minTime + ":" + frijoles4Time / minTime + ":" + springTime / minTime + ") (1:"
				+ springTime / frijoles4Time + ")");
	}

}
