package org.frijoles3.test.basic;

import java.awt.Color;

import org.frijoles3.test.ents.Dog;
import org.frijoles3.test.ents.Person;

public interface ITestingFactory {

	Long getResult(final ITestingFactory f);

	Long getOperand1(final ITestingFactory f);

	Long getOperand2(final ITestingFactory f);

	Color getPrototypeBlueColor(final ITestingFactory f);

	Color getSingleRedColor(final ITestingFactory f);

	Dog getChucho(final ITestingFactory f);

	Person getPerson(final ITestingFactory f);

}
