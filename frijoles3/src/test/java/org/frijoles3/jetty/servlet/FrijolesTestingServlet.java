package org.frijoles3.jetty.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.frijoles3.jetty.factory.ITestingWebContext;
import org.frijoles3.jetty.servlet.ents.DogsLister;
import org.frijoles3.jetty.servlet.ents.IDog;
import org.frijoles3.web.FactoryWebLoader;

import static org.junit.Assert.*;

/**
 * Servlet implementation class FormBindingServlet
 * 
 * <pre>
 * http://localhost:8080/ioc-web/FormBindingServlet
 * </pre>
 */
public class FrijolesTestingServlet extends HttpServlet {

	private static final long serialVersionUID = -1008441847011154887L;

	public static final String TITLE = "Frijoles test page";

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final ITestingWebContext factory = FactoryWebLoader.getFactory(request);

		assertNull(request.getAttribute("requestChucho"));
		final IDog requestChucho = factory.requestChucho(null, request);
		assertNotNull(request.getAttribute("requestChucho"));
		assertEquals(requestChucho, request.getAttribute("requestChucho"));

		assertNull(request.getSession().getAttribute("sessionChucho"));
		final IDog sessionChucho = factory.sessionChucho(null, request);
		assertNotNull(request.getSession().getAttribute("sessionChucho"));
		assertEquals(sessionChucho, request.getSession().getAttribute("sessionChucho"));

		final IDog singletonChucho = factory.singletonChucho(null);
		final IDog prototypeChucho = factory.prototypeChucho(null);
		final IDog threadChucho = factory.threadedChucho(null);

		assertEquals("request-chucho", requestChucho.getName());
		assertEquals("session-chucho", sessionChucho.getName());
		assertEquals("singleton-chucho", singletonChucho.getName());
		assertEquals("prototype-chucho", prototypeChucho.getName());
		assertEquals("threaded-chucho", threadChucho.getName());

		final IDog requestChucho2 = factory.requestChucho(null, request);
		final IDog sessionChucho2 = factory.sessionChucho(null, request);
		final IDog singletonChucho2 = factory.singletonChucho(null);
		final IDog prototypeChucho2 = factory.prototypeChucho(null);
		final IDog threadChucho2 = factory.threadedChucho(null);

		assertSame(requestChucho, requestChucho2);
		assertSame(sessionChucho, sessionChucho2);
		assertSame(singletonChucho, singletonChucho2);
		assertNotSame(prototypeChucho, prototypeChucho2);
		assertSame(threadChucho, threadChucho2);

		// /////////////////////////////////////////

		final DogsLister dogsLister = factory.getDogsLister(null, request);

		response.setContentType("text/html");
		final ServletOutputStream out = response.getOutputStream();
		out.println(TITLE);
		out.println(dogsLister.toString());
		out.flush();
	}

}
