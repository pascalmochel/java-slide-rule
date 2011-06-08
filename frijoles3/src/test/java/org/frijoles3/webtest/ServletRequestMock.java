package org.frijoles3.webtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ServletRequestMock implements ServletRequest {

	protected Map<String, Object> a;

	public ServletRequestMock() {
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

	// ---------------------------------------------

	public String getCharacterEncoding() {
		throw new UnsupportedOperationException();
	}

	public int getContentLength() {
		throw new UnsupportedOperationException();
	}

	public String getContentType() {
		throw new UnsupportedOperationException();
	}

	public ServletInputStream getInputStream() throws IOException {
		throw new UnsupportedOperationException();
	}

	public String getLocalAddr() {
		throw new UnsupportedOperationException();
	}

	public String getLocalName() {
		throw new UnsupportedOperationException();
	}

	public int getLocalPort() {
		throw new UnsupportedOperationException();
	}

	public Locale getLocale() {
		throw new UnsupportedOperationException();
	}

	public Enumeration getLocales() {
		throw new UnsupportedOperationException();
	}

	public String getParameter(final String name) {
		throw new UnsupportedOperationException();
	}

	public Map getParameterMap() {
		throw new UnsupportedOperationException();
	}

	public Enumeration getParameterNames() {
		throw new UnsupportedOperationException();
	}

	public String[] getParameterValues(final String name) {
		throw new UnsupportedOperationException();
	}

	public String getProtocol() {
		throw new UnsupportedOperationException();
	}

	public BufferedReader getReader() throws IOException {
		throw new UnsupportedOperationException();
	}

	public String getRealPath(final String path) {
		throw new UnsupportedOperationException();
	}

	public String getRemoteAddr() {
		throw new UnsupportedOperationException();
	}

	public String getRemoteHost() {
		throw new UnsupportedOperationException();
	}

	public int getRemotePort() {
		throw new UnsupportedOperationException();
	}

	public RequestDispatcher getRequestDispatcher(final String path) {
		throw new UnsupportedOperationException();
	}

	public String getScheme() {
		throw new UnsupportedOperationException();
	}

	public String getServerName() {
		throw new UnsupportedOperationException();
	}

	public int getServerPort() {
		throw new UnsupportedOperationException();
	}

	public boolean isSecure() {
		throw new UnsupportedOperationException();
	}

	public void setCharacterEncoding(final String env) throws UnsupportedEncodingException {
		throw new UnsupportedOperationException();
	}

}
