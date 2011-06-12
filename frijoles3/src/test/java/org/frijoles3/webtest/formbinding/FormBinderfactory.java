package org.frijoles3.webtest.formbinding;

import javax.servlet.http.HttpServletRequest;

public class FormBinderfactory implements IFormBinderfactory {

	public User getUserForm(final IFormBinderfactory self, final HttpServletRequest request) {
		final User r = new User();
		r.setName(request.getParameter("name"));
		r.setPass(request.getParameter("pass"));
		r.setAge(Integer.valueOf(request.getParameter("age")));
		r.setSex(Boolean.valueOf(request.getParameter("sex")));
		return r;
	}

}
