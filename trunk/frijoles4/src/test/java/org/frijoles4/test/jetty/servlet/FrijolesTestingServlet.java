package org.frijoles4.test.jetty.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.frijoles4.FrijolesContext;
import org.frijoles4.test.jetty.servlet.ents.DogImpl;
import org.frijoles4.test.jetty.servlet.ents.DogsLister;
import org.frijoles4.web.WebContextLoader;

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

		final FrijolesContext ctx = WebContextLoader.getContext(request);

		System.out.println(ctx.toString());
		assertNull(request.getAttribute("request-chucho"));
		final DogImpl requestChucho = ctx.getBean(DogImpl.class, "request-chucho", request); // requestChucho(null,
		// request);
		assertNotNull(request.getAttribute("request-chucho"));
		assertEquals(requestChucho, request.getAttribute("request-chucho"));

		assertNull(request.getSession().getAttribute("session-chucho"));
		final DogImpl sessionChucho = ctx.getBean(DogImpl.class, "session-chucho", request); // ctx.sessionChucho(null,
		// request);
		assertNotNull(request.getSession().getAttribute("session-chucho"));
		assertEquals(sessionChucho, request.getSession().getAttribute("session-chucho"));

		final DogImpl singletonChucho = ctx.getBean(DogImpl.class, "singleton-chucho", request); // ctx.singletonChucho(null);
		final DogImpl prototypeChucho = ctx.getBean(DogImpl.class, "prototype-chucho", request); // ctx.prototypeChucho(null);
		final DogImpl threadChucho = ctx.getBean(DogImpl.class, "threaded-chucho", request); // ctx.threadedChucho(null);

		assertEquals("request-chucho", requestChucho.getName());
		assertEquals("session-chucho", sessionChucho.getName());
		assertEquals("singleton-chucho", singletonChucho.getName());
		assertEquals("prototype-chucho", prototypeChucho.getName());
		assertEquals("threaded-chucho", threadChucho.getName());

		final DogImpl requestChucho2 = ctx.getBean(DogImpl.class, "request-chucho", request); // ctx.requestChucho(null,
		// request);
		final DogImpl sessionChucho2 = ctx.getBean(DogImpl.class, "session-chucho", request.getSession()); // ctx.sessionChucho(null,
		// request);
		final DogImpl singletonChucho2 = ctx.getBean(DogImpl.class, "singleton-chucho", request); // ctx.singletonChucho(null);
		final DogImpl prototypeChucho2 = ctx.getBean(DogImpl.class, "prototype-chucho", request); // ctx.prototypeChucho(null);
		final DogImpl threadChucho2 = ctx.getBean(DogImpl.class, "threaded-chucho", request); // ctx.threadedChucho(null);

		assertSame(requestChucho, requestChucho2);
		assertSame(sessionChucho, sessionChucho2);
		assertSame(singletonChucho, singletonChucho2);
		assertNotSame(prototypeChucho, prototypeChucho2);
		assertSame(threadChucho, threadChucho2);

		// /////////////////////////////////////////

		final DogsLister dogsLister = (DogsLister) ctx.getBean("dogs-lister", request); // ctx.getDogsLister(null,
		// request);

		response.setContentType("text/html");
		final ServletOutputStream out = response.getOutputStream();
		out.println(TITLE);
		out.println(dogsLister.toString());
		out.flush();
	}

}
