package org.frijoles4.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;

import org.frijoles4.FrijolesContext;
import org.frijoles4.exception.FrijolesException;

import java.util.logging.Logger;

public class ContextWebLoader implements ServletContextListener {

	public static final String CONTEXT_CLASS_INIT_PARAMETER = "factory-class";
	public static final String FRIJOLES_CONTEXT_ATTR_NAME = "frijoles-factory";

	protected static final Logger LOG = Logger.getLogger(ContextWebLoader.class.getName());

	protected FrijolesContext factory;

	public ContextWebLoader() {
		super();
	}

	public void contextInitialized(final ServletContextEvent sce) {

		final String contextClassName = sce.getServletContext()
				.getInitParameter(CONTEXT_CLASS_INIT_PARAMETER);

		if (contextClassName == null) {
			throw new FrijolesException("not found a <context-param> named '" + CONTEXT_CLASS_INIT_PARAMETER
					+ "'");
		}

		Class<?> factoryClass;
		try {
			factoryClass = Class.forName(contextClassName);
		} catch (final ClassNotFoundException e) {
			throw new FrijolesException("factory class not found: " + contextClassName, e);
		}
		this.factory = FrijolesContext.build(factoryClass);

		sce.getServletContext().setAttribute(FRIJOLES_CONTEXT_ATTR_NAME, factory);

		LOG.info("factory " + factoryClass.getSimpleName() + " successfully loaded in servlet context");
	}

	public void contextDestroyed(final ServletContextEvent sce) {
		this.factory = null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFactory(final HttpServletRequest request) {
		return (T) request.getSession().getServletContext().getAttribute(
				ContextWebLoader.FRIJOLES_CONTEXT_ATTR_NAME);
	}

}
