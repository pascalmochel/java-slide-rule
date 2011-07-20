package org.frijoles.binder.test;

import java.util.Date;

public class Bean {

	protected String name;
	protected Integer age;
	protected Date birthDate;

	public Bean() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(final Integer age) {
		this.age = age;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "Bean [name=" + name + ", age=" + age + ", birthDate=" + birthDate + "]";
	}

}
