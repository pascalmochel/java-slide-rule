package org.frijoles4.test;

import org.frijoles4.test.exception.ExceptionTest;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

public class M {

	public static void main(String[] args) {
		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));
		// junit.run(F1Test.class, BasicFactoryTest.class, DidYouMeanTest.class,PropsTest.class);
		junit.run(ExceptionTest.class);
	}

}
