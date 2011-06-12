package org.frijoles3.webtest.formbinding;

import javax.servlet.http.HttpServletRequest;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Request;

public interface IFormBinderfactory {

	@Scope(Request.class)
	User getUserForm(IFormBinderfactory self, HttpServletRequest request);
}
