package org.frijoles3.properties;

import org.frijoles3.anno.Scope;

public class F implements IF {

	protected PropertiesHolder props;

	public F() {
		this.props = new PropertiesHolder("frijoles-test");
	}

	@Scope
	public String getSalute() {
		return props.getProperty("salute");
	}

}
