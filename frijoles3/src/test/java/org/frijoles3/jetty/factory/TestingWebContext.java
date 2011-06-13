package org.frijoles3.jetty.factory;

import javax.servlet.http.HttpServletRequest;

import org.frijoles3.jetty.servlet.ents.DogImpl;
import org.frijoles3.jetty.servlet.ents.DogsLister;
import org.frijoles3.jetty.servlet.ents.IDog;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.List;

@Ignore
public class TestingWebContext implements ITestingWebContext {

	// proto
	public DogsLister getDogsLister(final ITestingWebContext self, final HttpServletRequest request) {
		final List<IDog> dogsList = new ArrayList<IDog>();
		dogsList.add(self.requestChucho(null, request));
		dogsList.add(self.sessionChucho(null, request));
		dogsList.add(self.singletonChucho(null));
		dogsList.add(self.prototypeChucho(null));
		dogsList.add(self.threadedChucho(null));
		return new DogsLister(dogsList);
	};

	public IDog requestChucho(final ITestingWebContext self, final HttpServletRequest request) {
		final IDog r = new DogImpl();
		r.setName("request-chucho");
		return r;
	};

	public IDog sessionChucho(final ITestingWebContext self, final HttpServletRequest request) {
		final IDog r = new DogImpl();
		r.setName("session-chucho");
		return r;
	};

	public final IDog singletonChucho(final ITestingWebContext self) {
		final IDog r = new DogImpl();
		r.setName("singleton-chucho");
		return r;
	};

	public IDog prototypeChucho(final ITestingWebContext self) {
		final IDog r = new DogImpl();
		r.setName("prototype-chucho");
		return r;
	};

	public IDog threadedChucho(final ITestingWebContext self) {
		final IDog r = new DogImpl();
		r.setName("threaded-chucho");
		return r;
	}

}
