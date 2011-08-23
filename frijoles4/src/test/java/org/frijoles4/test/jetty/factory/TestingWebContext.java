package org.frijoles4.test.jetty.factory;

import javax.servlet.http.HttpServletRequest;

import org.frijoles4.FrijolesContext;
import org.frijoles4.anno.Scope;
import org.frijoles4.scope.impl.Prototype;
import org.frijoles4.scope.impl.Request;
import org.frijoles4.scope.impl.Session;
import org.frijoles4.scope.impl.Singleton;
import org.frijoles4.scope.impl.Threaded;
import org.frijoles4.test.jetty.servlet.ents.DogImpl;
import org.frijoles4.test.jetty.servlet.ents.DogsLister;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.List;

@Ignore
public class TestingWebContext {

	@Scope(Request.class)
	public DogsLister getDogsLister(final FrijolesContext ctx, final HttpServletRequest request) {
		final List<DogImpl> dogsList = new ArrayList<DogImpl>();
		dogsList.add(ctx.getBean(DogImpl.class, "request-chucho", request));
		dogsList.add(ctx.getBean(DogImpl.class, "session-chucho", request));
		dogsList.add(ctx.getBean(DogImpl.class, "singleton-chucho", request));
		dogsList.add(ctx.getBean(DogImpl.class, "prototype-chucho", request));
		dogsList.add(ctx.getBean(DogImpl.class, "threaded-chucho", request));
		return new DogsLister(dogsList);
	};

	@Scope(Request.class)
	public DogImpl requestChucho(final FrijolesContext ctx, final HttpServletRequest request) {
		final DogImpl r = new DogImpl();
		r.setName("request-chucho");
		return r;
	};

	@Scope(Session.class)
	public DogImpl sessionChucho(final FrijolesContext ctx, final HttpServletRequest request) {
		final DogImpl r = new DogImpl();
		r.setName("session-chucho");
		return r;
	};

	@Scope(Singleton.class)
	public final DogImpl singletonChucho(final FrijolesContext ctx, final HttpServletRequest request) {
		final DogImpl r = new DogImpl();
		r.setName("singleton-chucho");
		return r;
	};

	@Scope(Prototype.class)
	public DogImpl prototypeChucho(final FrijolesContext ctx, final HttpServletRequest request) {
		final DogImpl r = new DogImpl();
		r.setName("prototype-chucho");
		return r;
	};

	@Scope(Threaded.class)
	public DogImpl threadedChucho(final FrijolesContext ctx, final HttpServletRequest request) {
		final DogImpl r = new DogImpl();
		r.setName("threaded-chucho");
		return r;
	}

}
