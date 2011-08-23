package org.frijoles4.test.jetty;

import org.frijoles4.test.jetty.servlet.FrijolesTestingServlet;
import org.frijoles4.test.jetty.servlet.JettyTestingServlet;
import org.frijoles4.web.WebContextLoader;
import org.junit.Test;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JettyServletErrorsTest {

	private static final String HOST = "localhost";
	private static final String CONTEXT_PATH = "/ioc-web";

	protected ServletTester tester;

	private void start(final String contextClass) throws Exception {
		this.tester = new ServletTester();
		this.tester.setContextPath(CONTEXT_PATH);
		this.tester.addServlet(JettyTestingServlet.class, "/JettyTestingServlet");
		this.tester.addServlet(FrijolesTestingServlet.class, "/FormBindingServlet");
		this.tester.addEventListener(new WebContextLoader());

		if (contextClass != null) {
			final Map<String, String> m = new HashMap<String, String>();
			m.put(WebContextLoader.FACTORY_CLASS_INIT_PARAMETER, contextClass);
			tester.getContext().setInitParams(m);
		}

		this.tester.start();
	}

	private void stop() throws Exception {
		this.tester.stop();
	}

	@Test
	public void testOk() throws Exception {

		start("org.frijoles4.test.jetty.factory.TestingWebContext");
		//
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
		//
		stop();
	}

	@Test
	public void testContextClassNotFound() throws Exception {

		start("org.frijoles.jetty.TestingWebContext2");
		// 
		final HttpTester request = new HttpTester();
		final HttpTester response = new HttpTester();
		request.setMethod("GET");
		request.setHeader(HOST, HOST);
		request.setURI("/ioc-web/FormBindingServlet");
		request.setVersion("HTTP/1.0");

		response.parse(tester.getResponses(request.generate()));

		System.out.println(response.getContent());
		assertTrue(response.getMethod() == null);
		assertEquals(404, response.getStatus());
		assertTrue(response.getContent().contains("HTTP ERROR: 404"));
		//
		stop();
	}

	@Test
	public void testContextClassNotDefined() throws Exception {

		start(null);

		//
		final HttpTester request = new HttpTester();
		final HttpTester response = new HttpTester();
		request.setMethod("GET");
		request.setHeader(HOST, HOST);
		request.setURI("/ioc-web/FormBindingServlet");
		request.setVersion("HTTP/1.0");

		response.parse(tester.getResponses(request.generate()));

		assertTrue(response.getMethod() == null);
		assertEquals(404, response.getStatus());
		assertTrue(response.getContent().contains("HTTP ERROR: 404"));
		//
		stop();
	}

	// @Test
	// public void testContextClassNotPublic() throws Exception {
	//
	// start("org.frijoles.jetty.basic.context.NonValidContext");
	//
	// //
	// final HttpTester request = new HttpTester();
	// final HttpTester response = new HttpTester();
	// request.setMethod("GET");
	// request.setHeader(HOST, HOST);
	// request.setURI("/ioc-web/FormBindingServlet");
	// request.setVersion("HTTP/1.0");
	//
	// response.parse(tester.getResponses(request.generate()));
	//
	// assertTrue(response.getMethod() == null);
	// assertEquals(404, response.getStatus());
	// assertTrue(response.getContent().contains("HTTP ERROR: 404"));
	// //
	// stop();
	// }

}
