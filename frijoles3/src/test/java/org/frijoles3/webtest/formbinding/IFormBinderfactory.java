package org.frijoles3.webtest.formbinding;

import javax.servlet.http.HttpServletRequest;

public interface IFormBinderfactory {

	User getUserForm(IFormBinderfactory self, HttpServletRequest request);
}
