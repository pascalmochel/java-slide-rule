package org.orm.criteria;

import org.orm.record.Entity;
import org.orm.record.field.identity.IdentityGenerator;
import org.orm.record.field.identity.impl.hsqldb.HsqldbIdentity;
import org.orm.record.field.regular.FString;
import org.orm.record.field.regular.primitive.FInteger;
import org.orm.record.field.regular.primitive.FLong;

public class A extends Entity {

	public static final IdentityGenerator<Long> id = new HsqldbIdentity<Long>(new FLong("ID_A"));

	public static final FString name = new FString("NAME");
	public static final FString city = new FString("CITY");
	public static final FInteger age = new FInteger("AGE");

	public A() {
		registerIdField(id);
		registerFields(name, city, age);
	}

	public Long getId() {
		return get(id);
	}

	public void setId(final Long id) {
		set(A.id, id);
	}

	public String getName() {
		return get(name);
	}

	public void setName(final String name) {
		set(A.name, name);
	}

	public String getCity() {
		return get(city);
	}

	public void setCity(final String city) {
		set(A.city, city);
	}

	public Integer getAge() {
		return get(age);
	}

	public void setAge(final Integer age) {
		set(A.age, age);
	}

}
