package org.frijoles3;

public class TestingFactory implements ITestingFactory {
	public Long getResult(final ITestingFactory f) {
		return f.getOperand1(null) * f.getOperand2(null);
	}

	public Long getOperand1(final ITestingFactory f) {
		return 5L;
	}

	public Long getOperand2(final ITestingFactory f) {
		return 3L;
	}
}