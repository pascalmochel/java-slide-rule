package org.frijoles3.jetty.factory;

import javax.servlet.http.HttpServletRequest;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Prototype;
import org.frijoles3.holder.impl.Request;
import org.frijoles3.holder.impl.Session;
import org.frijoles3.holder.impl.Singleton;
import org.frijoles3.holder.impl.Thread;
import org.frijoles3.jetty.servlet.ents.DogsLister;
import org.frijoles3.jetty.servlet.ents.IDog;

public interface ITestingWebContext {

	@Scope(Prototype.class)
	DogsLister getDogsLister(ITestingWebContext self, HttpServletRequest request);

	@Scope(Request.class)
	IDog requestChucho(ITestingWebContext self, HttpServletRequest request);

	@Scope(Session.class)
	IDog sessionChucho(ITestingWebContext self, HttpServletRequest request);

	@Scope(Singleton.class)
	IDog singletonChucho(ITestingWebContext self);

	@Scope(Prototype.class)
	IDog prototypeChucho(ITestingWebContext self);

	@Scope(Thread.class)
	IDog threadedChucho(ITestingWebContext self);

}