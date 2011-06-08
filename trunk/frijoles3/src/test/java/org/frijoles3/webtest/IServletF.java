package org.frijoles3.webtest;

import javax.servlet.ServletRequest;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Request;
import org.frijoles3.test.ents.Person;

public interface IServletF {

	@Scope(Request.class)
	Person getPerson(IServletF self, ServletRequest request);

}
