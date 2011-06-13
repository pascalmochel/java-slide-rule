package org.frijoles3.jetty.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FormBindingServlet
 * 
 * <pre>
 * http://localhost:8080/ioc-web/FormBindingServlet
 * </pre>
 */
public class JettyTestingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String TITLE = "Jetty works OK";

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		final ServletOutputStream out = response.getOutputStream();

		out.println("<html>");
		out.println("<head><TITLE>" + TITLE + "</TITLE></head>");
		out.println("<body>");
		out.println("<h1>" + TITLE + "</h1>");

		out.println("</body></html>");

		out.flush();
	}

}
