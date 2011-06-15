package org.frijoles3.webtest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class HttpSessionMock implements HttpSession {

	protected Map<String, Object> a;

	public HttpSessionMock() {
		super();
		this.a = new HashMap<String, Object>();
	}

	public void removeAttribute(final String name) {
		this.a.remove(name);
	}

	public void setAttribute(final String name, final Object o) {
		this.a.put(name, o);
	}

	public Object getAttribute(final String name) {
		return this.a.get(name);
	}

	public Enumeration<String> getAttributeNames() {
		throw new UnsupportedOperationException();
	}

	// --------------------------

	public long getCreationTime() {
		return 0;
	}

	public String getId() {
		return null;
	}

	public long getLastAccessedTime() {
		return 0;
	}

	public int getMaxInactiveInterval() {
		return 0;
	}

	public ServletContext getServletContext() {
		return null;
	}

	public HttpSessionContext getSessionContext() {
		return null;
	}

	public Object getValue(final String name) {
		return null;
	}

	public String[] getValueNames() {
		return null;
	}

	public void invalidate() {

	}

	public boolean isNew() {
		return false;
	}

	public void putValue(final String name, final Object value) {

	}

	public void removeValue(final String name) {

	}

	public void setMaxInactiveInterval(final int interval) {

	}

}
