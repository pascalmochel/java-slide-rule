package org.frijoles3.webtest.formbinding;

public class User {

	protected String name;
	protected String pass;
	protected Integer age;
	protected Boolean sex;

	public User() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(final String pass) {
		this.pass = pass;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(final Integer age) {
		this.age = age;
	}

	public Boolean getSex() {
		return sex;
	}

	public void setSex(final Boolean sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("User [name=").append(name).append(", pass=").append(pass).append(", age=")
				.append(age).append(", sex=").append(sex).append("]");
		return builder.toString();
	}

}
