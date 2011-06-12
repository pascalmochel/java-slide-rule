package org.frijoles3.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;

import org.frijoles3.FactoryBuilder;
import org.frijoles3.exception.FrijolesException;

import java.util.logging.Logger;

public class FactoryWebLoader implements ServletContextListener {

	public static final String FRIJOLES_CONTEXT_ATTR_NAME = "frijoles-factory";
	public static final String CONTEXT_CLASS_INIT_PARAMETER = "factory-class";

	protected static final Logger LOG = Logger.getLogger(FactoryWebLoader.class.getName());

	protected Object factory;

	public FactoryWebLoader() {
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
		this.factory = FactoryBuilder.build(factoryClass);

		sce.getServletContext().setAttribute(FRIJOLES_CONTEXT_ATTR_NAME, factory);

		LOG.info("factory " + factory.getClass().getSimpleName()
				+ " are successfully loaded in servlet context");
	}

	public void contextDestroyed(final ServletContextEvent sce) {
		this.factory = null;
	}

	@SuppressWarnings("unchecked")
	public <T> T getFactory(final HttpServletRequest request) {
		return (T) request.getSession().getServletContext().getAttribute(
				FactoryWebLoader.FRIJOLES_CONTEXT_ATTR_NAME);
	}
}
