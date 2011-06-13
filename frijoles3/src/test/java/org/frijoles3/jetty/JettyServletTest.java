package org.frijoles3.jetty;

import org.frijoles3.jetty.servlet.FrijolesTestingServlet;
import org.frijoles3.jetty.servlet.JettyTestingServlet;
import org.frijoles3.web.FactoryWebLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JettyServletTest {

	private static final String HOST = "localhost";
	private static final String CONTEXT_PATH = "/ioc-web";

	protected ServletTester tester;

	@Before
	public void beforeClass() throws Exception {

		this.tester = new ServletTester();
		this.tester.setContextPath(CONTEXT_PATH);

		this.tester.addServlet(JettyTestingServlet.class, "/JettyTestingServlet");
		// final ServletHolder sh =
		this.tester.addServlet(FrijolesTestingServlet.class, "/FormBindingServlet");

		this.tester.addEventListener(new FactoryWebLoader());

		final Map<String, String> m = new HashMap<String, String>();
		m.put(FactoryWebLoader.CONTEXT_CLASS_INIT_PARAMETER, "org.frijoles3.jetty.factory.TestingWebContext");
		tester.getContext().setInitParams(m);
		this.tester.start();
	}

	@After
	public void after() throws Exception {
		this.tester.stop();
	}

	@Test
	public void testThatJettyWorksFine() throws Exception {

		final HttpTester request = new HttpTester();
		final HttpTester response = new HttpTester();
		request.setMethod("GET");
		request.setHeader(HOST, HOST);
		request.setURI("/ioc-web/JettyTestingServlet");
		request.setVersion("HTTP/1.0");

		response.parse(tester.getResponses(request.generate()));

		assertTrue(response.getMethod() == null);
		assertEquals(200, response.getStatus());
		assertTrue(response.getContent().contains(JettyTestingServlet.TITLE));
	}

	@Test
	public void testThatFrijolesWorksFine() throws Exception {

		final HttpTester request = new HttpTester();
		final HttpTester response = new HttpTester();
		request.setMethod("GET");
		request.setHeader(HOST, HOST);
		request.setURI("/ioc-web/FormBindingServlet");
		request.setVersion("HTTP/1.0");

		response.parse(tester.getResponses(request.generate()));

		assertTrue(response.getMethod() == null);
		assertEquals(200, response.getStatus());
		System.out.println(response.getContent());
		assertTrue(response.getContent().contains(FrijolesTestingServlet.TITLE));
	}

}
