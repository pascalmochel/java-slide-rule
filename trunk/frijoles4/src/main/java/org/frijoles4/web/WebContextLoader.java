package org.frijoles4.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.frijoles4.FrijolesContext;
import org.frijoles4.exception.FrijolesException;

import java.util.logging.Logger;

public class WebContextLoader implements ServletContextListener {

	public static final String FACTORY_CLASS_INIT_PARAMETER = "factoryObject-class";
	public static final String FRIJOLES_CONTEXT_ATTR_NAME = "frijoles-context";

	protected static final Logger LOG = Logger.getLogger(WebContextLoader.class.getName());

	protected FrijolesContext context;

	public WebContextLoader() {
		super();
	}

	public void contextInitialized(final ServletContextEvent sce) {

		final String factoryClassName = sce.getServletContext()
				.getInitParameter(FACTORY_CLASS_INIT_PARAMETER);

		try {
			if (factoryClassName == null) {
				throw new FrijolesException("not found a <context-param> named '"
						+ FACTORY_CLASS_INIT_PARAMETER + "'");
			}

			Class<?> factoryClass;
			try {
				factoryClass = Class.forName(factoryClassName);
			} catch (final ClassNotFoundException e) {
				throw new FrijolesException("factoryObject class not found: " + factoryClassName, e);
			}
			this.context = FrijolesContext.build(factoryClass);

			sce.getServletContext().setAttribute(FRIJOLES_CONTEXT_ATTR_NAME, context);

			LOG.info("context successfully loaded in servlet context; factoryObject class is "
					+ factoryClass.getSimpleName());

		} catch (Exception e) {
			throw new FrijolesException("attempting to configure context from factoryObject: " + factoryClassName,
					e);
		}
	}

	public void contextDestroyed(final ServletContextEvent sce) {
	}

	public static FrijolesContext getContext(final HttpSession session) {
		return (FrijolesContext) session.getServletContext().getAttribute(
				WebContextLoader.FRIJOLES_CONTEXT_ATTR_NAME);
	}

	public static FrijolesContext getContext(final HttpServletRequest request) {
		return getContext(request.getSession());
	}

}
